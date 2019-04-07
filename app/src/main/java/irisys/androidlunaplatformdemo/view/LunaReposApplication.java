package irisys.androidlunaplatformdemo.view;

import android.app.Application;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import irisys.androidlunaplatformdemo.model.LunaService;
import kr.co.irisys.kmodule.kmodule;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class LunaReposApplication extends Application {

    private Retrofit retrofit;
    private LunaService lunaService; //Model Class
    private kmodule mKmodule = null;


    @Override
    public void onCreate() {
        super.onCreate();
        setupAPI();
    }

    private void setupAPI(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("API LOG", message);
            }
        });

        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).build();

        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("https://192.168.0.203:5000")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        lunaService = retrofit.create(LunaService.class);

    }

    public LunaService getLunaService(){
        return lunaService;
    }

    public kmodule getKmodule(int scale) {
        if(mKmodule == null) {
            String toPath = "/data/data/" + getPackageName() + "/data";
            File file = new File(toPath);
            if (!file.exists()) {
                copyAssetFolder(getAssets(), "data", toPath);
            }

            mKmodule = new kmodule(640, 480, scale, toPath);
        }
        return mKmodule;
    }

    public static boolean copyAssetFolder(AssetManager assetManager, String fromAssetPath, String toPath) {
        try {
            String[] files = assetManager.list(fromAssetPath);
            new File(toPath).mkdirs();
            boolean res = true;
            for (String file : files)
                if (file.contains("."))
                    res &= copyAsset(assetManager, fromAssetPath + "/" + file, toPath + "/" + file);
                else
                    res &= copyAssetFolder(assetManager, fromAssetPath + "/" + file, toPath + "/" + file);
            return res;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    private static boolean copyAsset(AssetManager assetManager, String fromAssetPath, String toPath) {
        InputStream in = null;
        OutputStream out = null;

        try {
            in = assetManager.open(fromAssetPath);
            new File(toPath).createNewFile();
            out = new FileOutputStream(toPath);
            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
