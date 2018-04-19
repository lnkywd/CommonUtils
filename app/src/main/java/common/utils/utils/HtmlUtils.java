package common.utils.utils;


import java.util.List;

import common.utils.view.RichTextEditor;

/**
 * Created by fzz on 2018/3/29 0029.
 */

public class HtmlUtils {
    private static String imgUrl;
    /**
     * img地址暂时未假数据 如有真数据 按照 拼接文字方式进行拼接
     */
    private static final String mHtmlItemImageHead ="<p><img src=";
    private static final String mHtmlItemImageEnd = "></p>";
    private static final String mHtmlItemContentHead = "<p>";
    private static final String mHtmlItemContentEnd = "</p> ";

    public static String toHtml(List<RichTextEditor.EditData> dataList) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).getBitmap() == null) {
                //根据换行符 进行拼接 html
                String content = dataList.get(i).getInputStr();
                String[] contents = content.split("\\n");
                for (int j = 0; j <contents.length ; j++) {
                    result.append(mHtmlItemContentHead);
                    result.append(contents[j]);
                    result.append(mHtmlItemContentEnd);
                }
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
