package common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Test;

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


}