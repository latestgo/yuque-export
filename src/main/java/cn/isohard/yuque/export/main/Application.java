package cn.isohard.yuque.export.main;

import cn.isohard.yuque.export.main.dt.*;
import cn.isohard.yuque.export.main.entity.Doc;
import cn.isohard.yuque.export.main.entity.DocDetail;
import cn.isohard.yuque.export.main.entity.Repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.util.CustomizableThreadCreator;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class Application {
    private static final String CONST_BASE = "https://www.yuque.com/api/v2";
    private static final String CONST_DOC = "/docs";
    private static final String CONST_USER = "/users";
    private static final String CONST_REPO = "/repos";

    private static final CustomizableThreadCreator factory = new CustomizableThreadFactory("yuque-thread-pool");
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws IOException {
        if (args.length == 3) {
//            String saveDir = "D:\\Administrator\\Downloads\\test";
            yuqueExport(args[0], args[1], args[2]);
        } else {
            System.out.println("参数不正确，保存路径 用户名 Token");
        }
    }

    private static void yuqueExport(String saveDir, String loginName, String token) {

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1, factory::createThread);

        String reposUrl = CONST_BASE + CONST_USER + "/" + loginName + CONST_REPO;
        List<Repo> repos = getList(reposUrl, RepoDt.class, token);
        Map<Repo, Future<Integer>> map = new HashMap<>();
        repos.forEach((Repo r) -> {
            String docsUrl = CONST_BASE + CONST_REPO + "/" + r.getNamespace() + CONST_DOC;

            if (!"Resource".equals(r.getType())) {
                Future<Integer> future = executor.submit(() -> {

                    AtomicInteger i = new AtomicInteger();
                    getList(docsUrl, DocDt.class, token).forEach((Doc d) -> {
                        i.getAndIncrement();
                        String docDetailUrl = docsUrl + "/" + d.getSlug();
                        DocDetail docDetail = getData(docDetailUrl, DocDetailDt.class, token);

                        log.info("处理文档：{} - {}", r.getName(), docDetail.getTitle());
                        String dir = saveDir + File.separator + r.getName();
                        String fileName = i.get() + "." + docDetail.getTitle() + ".md";
                        try {
                            saveMd(dir, fileName, docDetail.getBody());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    return i.get();
                });
                map.put(r, future);
            }
        });
        map.values().forEach(integerFuture -> {
            try {
                integerFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        map.forEach((Repo r, Future<Integer> future)-> {
            try {
                log.info("共处理 {} 知识库 {} 文档", r.getName(), future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        executor.shutdown();
    }

    private static void getUserInfo() {
        String result = restTemplate.getForObject(CONST_BASE + CONST_USER, String.class);
//        ResponseEntity<UserDt> result = restTemplate.exchange(, UserDt.class);
//        System.out.println(result.getBody());
    }

    private static RequestEntity<Void> getEntity(String url, String token) {
        return RequestEntity.get(url)
                .header("User-Agent", "Chrome")
                .header("X-Auth-Token", token)
                .build();
    }

    private static <T extends BaseDt<List<E>>, E> List<E> getList(String url, Class<T> clazz, String token) {
        RequestEntity<Void> requestEntity = getEntity(url, token);
        ResponseEntity<T> response = restTemplate.exchange(requestEntity, clazz);
        return response.getBody().getData();
    }

    private static <T extends BaseDt<E>, E> E getData(String url, Class<T> clazz, String token) {
        RequestEntity<Void> requestEntity = getEntity(url, token);
        ResponseEntity<T> response = restTemplate.exchange(requestEntity, clazz);
        return response.getBody().getData();
    }

    private static void saveMd(String dir, String filename, String md) throws IOException {
        File dirFile = new File(dir);
        dirFile.mkdirs();
        File file = new File(dirFile, filename);
        file.createNewFile();
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(md);
        }
    }
}
