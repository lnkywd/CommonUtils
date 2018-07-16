package common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;

import common.utils.utils.GsonUtils;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test() {
        Gson gson = new GsonBuilder()
                .serializeNulls()//序列化为null对象
                .registerTypeAdapterFactory(new GsonUtils.NullStringToEmptyAdapterFactory())
                .create();
        TestBean bean = gson.fromJson("{\"code\":\"200\",\"data\":[{\"list\":[{\"id\":\"59\",\"name\":\"errrrt\",\"uid\":\"58146\",\"gradeid\":\"1\",\"subjectid\":\"1\",\"answernum\":\"14\",\"createdate\":\"2018-04-24 11:46:05\",\"point\":\"0\",\"userInfo\":null,\"grade\":{\"id\":\"1\",\"name\":\"小学一年级\"},\"subject\":{\"Id\":\"1\",\"Name\":\"数学\"},\"images\":[{\"id\":\"458\",\"resourceid\":\"59\",\"url\":\"http:\\/\\/img.ajihua888.com\\/upload\\/2018\\/04\\/24\\/577972e9fbeb0fa5e7f35f5e36f7f382.jpg\"}],\"difftime\":\"2018-04-24 11:46:05\",\"href\":\"http:\\/\\/m.ajihua888.com\\/question\\/detail?id=59&uid=58146&type=2\"}],\"count\":\"1\",\"pageCount\":1,\"hasmore\":0}],\"message\":\"操作成功\"}", TestBean.class);
        System.out.print(bean.getData().get(0).getList().get(0).getUserInfo().getNickname());
    }

    @Test
    public void test2() throws JSONException {
//        JSONObject jsonObject = new JSONObject("{\"map\":{\"key1\":\"value1\",\"key2\":\"value2\"}}");
//        JSONObject content = jsonObject.getJSONObject("map");
        JSONObject jsonObject = new JSONObject("{\"userInfo\":{\"uid\":true,\"nickname\":\"刘元龙\",\"avatar\":\"http://img.doudou-le.com/upload/2018/03/31/de387ba1d59714d27cca328024c4cd2e.jpg\"}}");
        JSONObject jsonObject1 = jsonObject.getJSONObject("userInfo");
        Iterator<String> iterator = jsonObject1.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Object value = jsonObject1.get(key);
            if (value != null) {
                System.out.println("key=" + key + "---value=" + value.toString());
            }
        }
    }

    @Test
    public void test3() {
        int[] array = new int[]{3, 5, 6, 1, 3, 13, 9, 2};
        sort(array);
        System.out.println(Arrays.toString(array));
    }

    public void sort(int[] arrays) {
        int lastIndex = 0;
        int border = arrays.length - 1;
        for (int i = 0; i < arrays.length; i++) {
            boolean isNoChange = true;
            for (int j = 0; j < border; j++) {
                if (arrays[j] > arrays[j + 1]) {
                    int temp = arrays[j];
                    arrays[j] = arrays[j + 1];
                    arrays[j + 1] = temp;
                    isNoChange = false;
                    lastIndex = j;
                }
            }
            border = lastIndex;
            if (isNoChange) {
                break;
            }
        }
    }


}