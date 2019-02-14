package org.wltea.analyzer.lucene;

import com.jorado.ik.ReloadableTokenizerFactory;
import com.jorado.ik.util.HttpUtils;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.ResourceLoader;
import org.apache.lucene.util.AttributeFactory;
import org.wltea.analyzer.dic.Dictionary;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IKTokenizerFactory extends ReloadableTokenizerFactory {

    private boolean useSmart;

    public IKTokenizerFactory(Map<String, String> args) {
        super(args);
        this.useSmart = getBoolean(args, "useSmart", false);
    }

    public Tokenizer create(AttributeFactory factory) {
        return new IKTokenizer(factory, this.useSmart);
    }

    @Override
    public void update(final ResourceLoader loader, final String paths) {

        try {

            List<String> dicPaths = SplitFileNames(paths);

            List<InputStream> streams = new ArrayList<>(dicPaths.size());
            dicPaths.forEach(path -> {
                try {
                    streams.add(loader.openResource(path));
                } catch (IOException e) {
                    System.out.println("missing dict source : " + path);
                }
            });

            Dictionary.addDic2MainDic(streams);
            System.out.println("reload finish! " + dicPaths);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateRemote(final String paths) {

        try {

            List<String> dicPaths = SplitFileNames(paths);

            List<InputStream> streams = new ArrayList<>(dicPaths.size());
            dicPaths.forEach(path -> {
                try {
                    //下载文件
                    String body = HttpUtils.get(path);
                    InputStream inputStream = new ByteArrayInputStream(body.getBytes());
                    streams.add(inputStream);
                } catch (Exception e) {
                    System.out.println("missing dict source : " + path);
                }
            });

            Dictionary.addDic2MainDic(streams);
            System.out.println("reload finish! " + dicPaths);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}