package com.majestic.data;

import com.majestic.L2ClientDat;
import com.majestic.clientcryptor.DatFile;
import com.majestic.clientcryptor.crypt.DatCrypter;
import com.majestic.config.ConfigDebug;
import com.majestic.util.ByteReader;
import com.majestic.util.ByteWriter;
import com.majestic.util.Util;
import com.majestic.xml.ParamNode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GameDataName {
    private static final byte[] END_FILE_BYTES = {12, 83, 97, 102, 101, 80, 97, 99, 107, 97, 103, 101, 0};

    private final Lock _lock = new ReentrantLock();
    private final Map<Integer, String> _names = new TreeMap<>();
    private final Map<String, Integer> _nameHash = new HashMap<>();
    private File _currDataNameFile = null;

    public GameDataName() {
    }

    private void load(File currentFile, DatCrypter decCrypter) throws Exception {
        _lock.lock();
        try {
            _names.clear();
            _nameHash.clear();
            if (decCrypter.isEncrypt()) {
                final File file = new File(currentFile.getParent(), "L2GameDataName.txt");
                if (file.equals(_currDataNameFile)) {
                    return;
                }

                if (file.exists()) {
                    final List<String> list = Files.readAllLines(file.toPath());
                    for (int i = 0; i < list.size(); ++i) {
                        final String str = list.get(i);
                        final Map<String, String> map = Util.stringToMap(str);
                        String name = map.get("name");
                        name = name.replaceAll("^\\[(.*?)]$", "$1");
                        _names.put(i, name);
                        if (!_nameHash.containsKey(name.toLowerCase())) {
                            _nameHash.put(name.toLowerCase(), i);
                        } else {
                            L2ClientDat.addLogConsole("GameDataName: Contains dublicate value: [" + name + "] in index[" + i + "]!", true);
                        }
                    }
                    L2ClientDat.addLogConsole("GameDataName: Load " + _names.size() + " count.", true);
                } else {
                    L2ClientDat.addLogConsole("GameDataName: File '" + file.getName() + "' not found!", true);
                }
                _currDataNameFile = file;
            } else {
                final File file = new File(currentFile.getParent(), "L2GameDataName.dat");
                if (file.equals(_currDataNameFile)) {
                    return;
                }

                if (file.exists()) {
                    final FileInputStream fis = new FileInputStream(file);
                    if (fis.available() < 28) {
                        L2ClientDat.addLogConsole(file.getName() + " The file is too small.", true);
                        fis.close();
                        return;
                    }

                    final byte[] head = new byte[28];
                    fis.read(head);
                    fis.close();
                    final String header = new String(head, StandardCharsets.UTF_16LE);
                    if (!header.startsWith("Lineage2Ver")) {
                        L2ClientDat.addLogConsole("GameDataName: File " + file.getName() + " not encrypted. Skip decrypt.", true);
                        return;
                    }

                    if (Integer.parseInt(header.substring(11)) != decCrypter.getCode()) {
                        L2ClientDat.addLogConsole("GameDataName: File " + file.getName() + " encrypted code: " + header + ". Skip decrypt.", true);
                        return;
                    }

                    L2ClientDat.addLogConsole("Unpacking [" + file.getName() + "]", true);
                    final DatFile dat = new DatFile(file.getAbsolutePath());
                    dat.decrypt(decCrypter);
                    final ByteBuffer buff = dat.getBuff();
                    for (int size = ByteReader.readUInt(buff), j = 0; j < size; ++j) {
                        final String name2 = ByteReader.readUtfString(buff, false);
                        _names.put(j, name2);
                        if (!_nameHash.containsKey(name2.toLowerCase())) {
                            _nameHash.put(name2.toLowerCase(), j);
                        } else {
                            L2ClientDat.addLogConsole("GameDataName: Contains dublicate value: [" + name2 + "] in index[" + j + "]!", true);
                        }
                    }
                    L2ClientDat.addLogConsole("GameDataName: Load " + _names.size() + " count.", true);
                } else {
                    L2ClientDat.addLogConsole("GameDataName: File '" + file.getName() + "' not found!", true);
                }
                _currDataNameFile = file;
            }
        } finally {
            _lock.unlock();
        }
    }

    public String getString(File currentFile, DatCrypter crypter, int index, boolean mass) throws Exception {
        _lock.lock();
        try {
            if (_currDataNameFile == null) {
                load(currentFile, crypter);
            }

            if (!mass && !_names.containsKey(index) && _currDataNameFile.exists()) {
                L2ClientDat.addLogConsole("GameDataName: Not found string for index: " + index, true);
                return "[None]";
            }

            final String val = _names.getOrDefault(index, "<StrID:" + index + ">");
            if (!mass && val.isEmpty()) {
                L2ClientDat.addLogConsole("GameDataName: String name Empty!!! Index: " + index + ", file: " + currentFile.getName(), true);
            }

            return "[" + val + "]";
        } finally {
            _lock.unlock();
        }
    }

    public int getId(File currentFile, DatCrypter crypter, ParamNode node, String str, boolean mass) throws Exception {
        if ((!str.startsWith("[") || !str.endsWith("]")) && !mass) {
            L2ClientDat.addLogConsole("GameDataName: String name not brackets!!! file: " + currentFile.getName() + " str: " + str + " node: " + node, true);
        }

        str = str.substring(1, str.length() - 1);
        if (str.isEmpty()) {
            if (!mass) {
                L2ClientDat.addLogConsole("GameDataName: String name Empty!!! file: " + currentFile.getName() + ", node: " + node, true);
            }
            return -1;
        }

        _lock.lock();
        try {
            if (_currDataNameFile == null) {
                load(currentFile, crypter);
            }

            if (_nameHash.containsKey(str.toLowerCase())) {
                return _nameHash.get(str.toLowerCase());
            }

            if (str.matches("^<StrID:(\\d+)>$")) {
                return Integer.parseInt(str.replaceAll("^<StrID:(\\d+)>$", "$1"));
            }

            final int newIndex = _names.size();
            _names.put(newIndex, str);
            _nameHash.put(str.toLowerCase(), newIndex);
            return newIndex;
        } finally {
            _lock.unlock();
        }
    }

    public void checkAndUpdate(String currentDir, DatCrypter crypter) throws Exception {
        _lock.lock();
        try {
            if (!_nameHash.isEmpty()) {
                final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                baos.write(ByteWriter.writeInt(_names.size()));
                for (String key : _names.values()) {
                    baos.write(ByteWriter.writeUtfString(key, false));
                }

                final byte[] bytes = baos.toByteArray();
                final byte[] resultBytes = new byte[bytes.length + GameDataName.END_FILE_BYTES.length];
                System.arraycopy(bytes, 0, resultBytes, 0, bytes.length);
                System.arraycopy(GameDataName.END_FILE_BYTES, 0, resultBytes, bytes.length, GameDataName.END_FILE_BYTES.length);

                final String file = currentDir + "/L2GameDataName.dat";
                if (ConfigDebug.ENCRYPT) {
                    DatFile.encrypt(resultBytes, file, crypter);
                } else {
                    final FileOutputStream os = new FileOutputStream(file, false);
                    os.write(resultBytes);
                    os.close();
                }

                L2ClientDat.addLogConsole("GameDataName: Packed " + _names.size() + " count.", true);
            }
        } finally {
            _lock.unlock();
        }
    }

    public void clear() {
        _lock.lock();
        try {
            _names.clear();
            _nameHash.clear();
            _currDataNameFile = null;
        } finally {
            _lock.unlock();
        }
    }

    public static GameDataName getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        protected static final GameDataName INSTANCE = new GameDataName();
    }
}
