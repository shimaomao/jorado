package com.jorado.core.util;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.util.*;

/*
 * Created by len.zhang on 2018/4/17.
 *
 */

public final class IOUtils {

    private static final int BUFFER_SIZE = 1024 * 8;
    private static final String DEFAULT_CHARSET = "UTF-8";

    private IOUtils() {
    }

    /**
     * write.
     *
     * @param is InputStream instance.
     * @param os OutputStream instance.
     * @return count.
     * @throws IOException
     */
    public static long write(InputStream is, OutputStream os) throws IOException {
        return write(is, os, BUFFER_SIZE);
    }

    /**
     * write.
     *
     * @param is         InputStream instance.
     * @param os         OutputStream instance.
     * @param bufferSize buffer size.
     * @return count.
     * @throws IOException
     */
    public static long write(InputStream is, OutputStream os, int bufferSize) throws IOException {
        int read;
        long total = 0;
        byte[] buff = new byte[bufferSize];
        while (is.available() > 0) {
            read = is.read(buff, 0, buff.length);
            if (read > 0) {
                os.write(buff, 0, read);
                total += read;
            }
        }
        return total;
    }

    /**
     * write string.
     *
     * @param writer Writer instance.
     * @param string String.
     * @throws IOException
     */
    public static long write(Writer writer, String string) throws IOException {
        Reader reader = new StringReader(string);
        try {
            return write(reader, writer);
        } finally {
            reader.close();
        }
    }

    /**
     * write.
     *
     * @param reader Reader.
     * @param writer Writer.
     * @return count.
     * @throws IOException
     */
    public static long write(Reader reader, Writer writer) throws IOException {
        return write(reader, writer, BUFFER_SIZE);
    }

    /**
     * write.
     *
     * @param reader     Reader.
     * @param writer     Writer.
     * @param bufferSize buffer size.
     * @return count.
     * @throws IOException
     */
    public static long write(Reader reader, Writer writer, int bufferSize) throws IOException {
        int read;
        long total = 0;
        char[] buf = new char[bufferSize];
        while ((read = reader.read(buf)) != -1) {
            writer.write(buf, 0, read);
            total += read;
        }
        return total;
    }

    /**
     * write lines.
     *
     * @param os    output stream.
     * @param lines lines.
     */
    public static void writeLines(OutputStream os, String[] lines) {
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(os));
        try {
            for (String line : lines)
                writer.println(line);
            writer.flush();
        } finally {
            writer.close();
        }
    }

    /**
     * write lines.
     *
     * @param file  file.
     * @param lines lines.
     * @throws IOException
     */
    public static void writeLines(File file, String[] lines) throws IOException {
        if (file == null)
            throw new IOException("File is null.");
        writeLines(new FileOutputStream(file), lines);
    }

    /**
     * append lines.
     *
     * @param file  file.
     * @param lines lines.
     * @throws IOException
     */
    public static void appendLines(File file, String[] lines) throws IOException {
        if (file == null)
            throw new IOException("File is null.");
        writeLines(new FileOutputStream(file, true), lines);
    }

    /**
     * write lines.
     *
     * @param os    output stream.
     * @param lines lines.
     * @throws IOException
     */
    public static void writeLines(OutputStream os, Collection<String> lines, String charsetName) throws IOException {
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(os, charsetName));
        try {
            for (String line : lines)
                writer.println(line);
            writer.flush();
        } finally {
            writer.close();
        }
    }

    /**
     * write lines.
     *
     * @param file  file.
     * @param lines lines.
     * @throws IOException
     */
    public static void writeLines(File file, Collection<String> lines, String charsetName) throws IOException {
        if (file == null)
            throw new IOException("File is null.");
        writeLines(new FileOutputStream(file), lines, charsetName);
    }

    /**
     * write lines.
     *
     * @param filePath filePath.
     * @param lines    lines.
     * @throws IOException
     */
    public static void writeLines(String filePath, Collection<String> lines, String charsetName) throws IOException {
        if (StringUtils.isNullOrWhiteSpace(filePath))
            throw new IOException("File Path is null.");

        File file = new File(filePath);

        writeLines(new FileOutputStream(file), lines, charsetName);
    }

    /**
     * write lines.
     *
     * @param filePath filePath.
     * @param lines    lines.
     * @throws IOException
     */
    public static void writeLines(String filePath, Collection<String> lines) throws IOException {
        writeLines(filePath, lines, "UTF-8");
    }

    /**
     * append lines.
     *
     * @param file  file.
     * @param lines lines.
     * @throws IOException
     */
    public static void appendLines(File file, Collection<String> lines, String charsetName) throws IOException {
        if (file == null)
            throw new IOException("File is null.");
        writeLines(new FileOutputStream(file, true), lines, charsetName);
    }

    /**
     * append lines.
     *
     * @param filePath file.
     * @param lines    lines.
     * @throws IOException
     */
    public static void appendLines(String filePath, Collection<String> lines, String charsetName) throws IOException {
        if (StringUtils.isNullOrWhiteSpace(filePath))
            throw new IOException("File Path is null.");

        File file = new File(filePath);

        writeLines(new FileOutputStream(file, true), lines, charsetName);
    }

    /**
     * append lines.
     *
     * @param filePath file.
     * @param lines    lines.
     * @throws IOException
     */
    public static void appendLines(String filePath, Collection<String> lines) throws IOException {
        appendLines(filePath, lines, "UTF-8");
    }

    public static char[] readToChars(Reader reader) {
        try {
            StringBuilder buffer = new StringBuilder();
            char[] buf = new char[8192];
            int len;
            while ((len = reader.read(buf)) != -1) {
                buffer.append(buf, 0, len);
            }
            char[] result = new char[buffer.length()];
            buffer.getChars(0, buffer.length(), result, 0);
            return result;
        } catch (Exception ex) {
            return new char[]{};
        } finally {
            try {
                reader.close();
            } catch (Exception ex) {

            }
        }
    }

    public static String readToString(Reader reader) {
        try {
            StringBuilder buffer = new StringBuilder();
            char[] buf = new char[8192];
            int len;
            while ((len = reader.read(buf)) != -1) {
                buffer.append(buf, 0, len);
            }
            return buffer.toString();
        } catch (Exception ex) {
            return null;
        } finally {
            try {
                reader.close();
            } catch (Exception ex) {

            }
        }
    }

    public static List<String> readLines(Reader reader) {
        BufferedReader bufferedReader = new BufferedReader(reader);
        try {
            List<String> lines = new ArrayList<String>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (Exception ex) {
            return new ArrayList<>();
        } finally {
            try {
                bufferedReader.close();
            } catch (Exception ex) {

            }
        }
    }

    public static List<String> readLines(Reader reader, boolean removeDuplicate) {
        List<String> lines = readLines(reader);
        if (!removeDuplicate) return lines;

        LinkedHashSet<String> set = new LinkedHashSet<>(lines.size());
        set.addAll(lines);
        lines.clear();
        lines.addAll(set);
        return lines;
    }

    public static List<String> readLines(Reader reader, Filter<String> filter) {
        BufferedReader bufferedReader = new BufferedReader(reader);
        try {
            List<String> lines = new ArrayList<String>();
            String line;
            while (StringUtils.isNotNullOrWhiteSpace(line = bufferedReader.readLine())) {
                line = filter.filter(line);
                if (StringUtils.isNotNullOrWhiteSpace(line)) {
                    lines.add(line);
                }
            }
            return lines;
        } catch (Exception ex) {
            return new ArrayList<>();
        } finally {
            try {
                bufferedReader.close();
            } catch (Exception ex) {

            }
        }
    }

    public static String read(String filePath) {
        return read(filePath, DEFAULT_CHARSET);
    }

    public static String read(String filePath, String charsetName) {

        InputStreamReader reader = null;
        try {
            File file = new File(filePath);
            reader = new InputStreamReader(new FileInputStream(file), charsetName);
            return readToString(reader);
        } catch (Exception ex) {
            return null;
        } finally {
            try {
                if (null != reader)
                    reader.close();
            } catch (Exception ex) {

            }
        }
    }

    public static String read(InputStream inputStream) {
        return read(inputStream, DEFAULT_CHARSET);
    }

    public static String read(InputStream inputStream, String charsetName) {

        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(inputStream, charsetName);
            return readToString(reader);
        } catch (Exception ex) {
            return null;
        } finally {
            try {
                if (null != reader)
                    reader.close();
            } catch (Exception ex) {

            }
        }
    }

    public static List<String> readLines(String filePath, String charsetName) {
        InputStreamReader reader = null;
        try {
            File file = new File(filePath);
            reader = new InputStreamReader(new FileInputStream(file), charsetName);
            return readLines(reader);
        } catch (Exception ex) {
            return new ArrayList<>();
        } finally {
            try {
                if (null != reader)
                    reader.close();
            } catch (Exception ex) {

            }
        }
    }

    public static List<String> readLines(String filePath) {

        return readLines(filePath, "UTF-8");
    }

    public static List<String> readLines(InputStream inputStream, String charsetName) {
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(inputStream, charsetName);
            return readLines(reader);
        } catch (Exception ex) {
            return new ArrayList<>();
        } finally {
            try {
                if (null != reader)
                    reader.close();
            } catch (Exception ex) {
            }
        }
    }

    public static List<String> readLines(InputStream inputStream) {

        return readLines(inputStream, "UTF-8");
    }

    public static List<String> readLines(String filePath, String charsetName, boolean removeDuplicate) {
        InputStreamReader reader = null;
        try {
            File file = new File(filePath);
            reader = new InputStreamReader(new FileInputStream(file), charsetName);
            return readLines(reader, removeDuplicate);
        } catch (Exception ex) {
            return new ArrayList<>();
        } finally {
            try {
                if (null != reader)
                    reader.close();
            } catch (Exception ex) {

            }
        }
    }

    public static List<String> readLines(String filePath, boolean removeDuplicate) {

        return readLines(filePath, "UTF-8", removeDuplicate);
    }

    public static List<String> readLines(InputStream inputStream, String charsetName, boolean removeDuplicate) {
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(inputStream, charsetName);
            return readLines(reader, removeDuplicate);
        } catch (Exception ex) {
            return new ArrayList<>();
        } finally {
            try {
                if (null != reader)
                    reader.close();
            } catch (Exception ex) {
            }
        }
    }

    public static List<String> readLines(InputStream inputStream, boolean removeDuplicate) {

        return readLines(inputStream, "UTF-8", removeDuplicate);
    }


    public static List<String> readLines(String filePath, String charsetName, Filter<String> filter) {
        InputStreamReader reader = null;
        try {
            File file = new File(filePath);
            reader = new InputStreamReader(new FileInputStream(file), charsetName);
            return readLines(reader, filter);
        } catch (Exception ex) {
            return new ArrayList<>();
        } finally {
            try {
                if (null != reader)
                    reader.close();
            } catch (Exception ex) {

            }
        }
    }

    public static List<String> readLines(String filePath, Filter<String> filter) {

        return readLines(filePath, "UTF-8", filter);
    }

    public static List<String> readLines(InputStream inputStream, String charsetName, Filter<String> filter) {
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(inputStream, charsetName);
            return readLines(reader, filter);
        } catch (Exception ex) {
            return new ArrayList<>();
        } finally {
            try {
                if (null != reader)
                    reader.close();
            } catch (Exception ex) {
            }
        }
    }

    public static List<String> readLines(InputStream inputStream, Filter<String> filter) {

        return readLines(inputStream, "UTF-8", filter);
    }

    public static void copy(Reader in, Writer out) {
        try {
            char[] buf = new char[8192];
            int len;
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
        } catch (Exception ex) {

        } finally {
            try {
                in.close();
            } catch (Exception ex) {

            }
        }
    }

    public static void copy(InputStream in, OutputStream out) {
        try {
            byte[] buf = new byte[8192];
            int len;
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
        } catch (Exception ex) {

        } finally {
            try {
                in.close();
            } catch (Exception ex) {

            }
        }
    }

    public static boolean exists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    public static void delete(String... filePaths) {
        for (String path : filePaths) {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public static boolean delete(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return file.delete();
        }
        return true;
    }

    /**
     * 单列文本内容映射为字典,便于快速查找
     *
     * @param filePath
     * @return
     */
    public static Map<String, String> fileToMap(String filePath) {

        Map<String, String> map = new LinkedHashMap<>();

        try {
            List<String> lines = IOUtils.readLines(filePath);

            String[] fields;
            String k;
            String v;

            for (String line : lines) {
                fields = StringUtils.splitString(line, ",", false);
                k = fields[0];
                v = fields.length > 1 ? fields[1] : "";
                if (!map.containsKey(k))
                    map.put(k, v);
            }
        } catch (Exception ex) {

        }
        return map;
    }

    /**
     * 单列文本内容映射为字典,便于快速查找
     *
     * @param name
     * @return
     */
    public static Map<String, String> resourceToMap(String name) {

        Map<String, String> map = new LinkedHashMap<>();

        try {
            List<String> lines = IOUtils.readResourceLines(name);

            String[] fields;
            String k;
            String v;


            for (String line : lines) {
                fields = StringUtils.splitString(line, ",", false);
                k = fields[0];
                v = fields.length > 1 ? fields[1] : "";
                if (!map.containsKey(k))
                    map.put(k, v);
            }
        } catch (Exception ex) {

        }
        return map;
    }

    /**
     * 获取映射路径
     *
     * @param path
     * @return
     */
    public static String mapPath(String path) {
        return mapPath() + path;
    }

    public static String mapPath() {
        String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        return basePath.substring(1);
    }

    public static InputStream getResourceAsStream(String name) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
    }

    public static String readResource(String name) {
        return readResource(name, DEFAULT_CHARSET);
    }

    public static List<String> readResourceLines(String name) {
        return readResourceLines(name, DEFAULT_CHARSET);
    }

    public static String readResource(String name, String charsetName) {
        return read(getResourceAsStream(name), charsetName);
    }

    public static List<String> readResourceLines(String name, String charsetName) {
        return readLines(getResourceAsStream(name), charsetName);
    }

    /**
     * 静默关闭
     *
     * @param closeable 可关闭的
     */
    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception ignore) {
                // NOPMD
            }
        }
    }

    /**
     * 静默关闭 for jdk6
     *
     * @param closeable 可关闭的
     */
    public static void closeQuietly(ServerSocket closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception ignore) {
                // NOPMD
            }
        }
    }

    /**
     * 静默关闭 for jdk6
     *
     * @param closeable 可关闭的
     */
    public static void closeQuietly(Socket closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception ignore) {
                // NOPMD
            }
        }
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 文件夹
     * @return 是否删除完成
     */
    public static boolean cleanDirectory(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (String aChildren : children) {
                    boolean success = cleanDirectory(new File(dir, aChildren));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        return dir.delete();
    }

    /**
     * 合并记录并去重
     *
     * @param lines
     * @return
     */
    public static List<String> margin(List<String>... lines) {
        List<String> result = new ArrayList<>();
        LinkedHashSet<String> set = new LinkedHashSet<>();
        for (List<String> list : lines) {
            set.addAll(list);
        }
        result.addAll(set);
        return result;
    }

    /**
     * 重命名文件
     *
     * @param from
     * @param to
     */
    public static void renameFile(String from, String to) {
        new File(from).renameTo(new File(to));
    }

    /**
     * 复制文件
     *
     * @param source
     * @param dest
     * @throws IOException
     */
    public static void copyFile(String source, String dest) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(new File(source)).getChannel();
            outputChannel = new FileOutputStream(new File(dest)).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }

    public static void saveFile(String path, String content) {
        File file = new File(path);
        try {
            if (!file.exists()) {
                createDirectory(file.getParent());
                file.createNewFile();
            }
            PrintWriter writer = new PrintWriter(file, "UTF-8");
            writer.println(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createDirectory(String... paths) {
        for (String path : paths) {
            File file = new File(path);
            file.mkdirs();
        }
    }
}