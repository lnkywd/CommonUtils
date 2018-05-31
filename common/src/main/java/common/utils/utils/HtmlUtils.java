package common.utils.utils;


import java.util.List;

import common.utils.view.richtext.RichTextEditor;

/**
 * Created by fzz on 2018/3/29 0029.
 */

public class HtmlUtils {
    /**
     * img地址暂时未假数据 如有真数据 按照 拼接文字方式进行拼接
     */
    private static final String mHtmlItemImageHead = "<p><img src=";
    private static final String mHtmlItemImageEnd = "></p>";
    private static final String mHtmlItemVideoHead = "<p><video src=";
    private static final String mHtmlItemVideoEnd = " controls=\"controls\" poster=\"http://m.doudou-le.com/images/appbanner.jpg\"></p>";
    private static final String mHtmlItemContentHead = "<p>";
    private static final String mHtmlItemContentEnd = "</p> ";
    private static String imgUrl;

    public static String toHtml(List<RichTextEditor.EditData> dataList) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < dataList.size(); i++) {
            RichTextEditor.EditData editData = dataList.get(i);
            if (dataList.get(i).getBitmap() == null) {
                //根据换行符 进行拼接 html
                String content = dataList.get(i).getInputStr();
                String[] contents = content.split("\\n");
                for (int j = 0; j < contents.length; j++) {
                    result.append(mHtmlItemContentHead);
                    result.append(contents[j]);
                    result.append(mHtmlItemContentEnd);
                }
            } else if (editData.isVideo()) {
                result.append(mHtmlItemVideoHead);
                result.append(editData.getImagePath());
                result.append(mHtmlItemVideoEnd);
            } else {
                imgUrl = dataList.get(i).getImagePath();
                result.append(mHtmlItemImageHead);
                result.append(imgUrl);
                result.append(mHtmlItemImageEnd);
            }
        }
        LogUtils.e(result);
        return result.toString();
    }

}
