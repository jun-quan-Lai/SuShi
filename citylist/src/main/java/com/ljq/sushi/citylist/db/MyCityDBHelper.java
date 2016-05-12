package com.ljq.sushi.citylist.db;

import android.content.Context;
import android.os.Environment;

import com.ljq.sushi.citylist.common.L;
import com.ljq.sushi.citylist.common.T;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/5/12.
 */
public class MyCityDBHelper {

    Context context;
    private CityDB mCityDB;

    public MyCityDBHelper(Context context) {
        super();
        this.context = context;
    }

    public synchronized CityDB getCityDB() {
        if (mCityDB == null)
            mCityDB = openCityDB();
        return mCityDB;
    }

    private CityDB openCityDB() {
        String path = "/data"
                + Environment.getDataDirectory().getAbsolutePath()
                + File.separator + "com.ljq.sushi"
                + File.separator + CityDB.CITY_DB_NAME;
        File db = new File(path);
        if (!db.exists()) {
            L.i("db is not exists");
            try {
                InputStream is = context.getAssets().open("city.db");
                FileOutputStream fos = new FileOutputStream(db);
                int len = -1;
                byte[] buffer = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    fos.flush();
                }
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
                T.showLong(context, e.getMessage());
                System.exit(0);
            }
        }
        return new CityDB(context, path);
    }
}

