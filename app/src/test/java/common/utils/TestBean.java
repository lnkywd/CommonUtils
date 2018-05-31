package common.utils;

import java.util.List;

/**
 * @author wd
 * @date 2018/05/24
 * Email 18842602830@163.com
 * Description
 */

public class TestBean {

    /**
     * code : 200
     * data : [{"list":[{"id":"59","name":"errrrt","uid":"58146","gradeid":"1","subjectid":"1","answernum":"14","createdate":"2018-04-24 11:46:05","point":"0","userInfo":{"uid":"58146","nickname":"wudi","avatar":"http://img.ajihua888.com/upload/2018/04/04/dcc98c324857a0ab2e5cf13bc0f5cc87.jpg"},"grade":{"id":"1","name":"小学一年级"},"subject":{"Id":"1","Name":"数学"},"images":[{"id":"458","resourceid":"59","url":"http://img.ajihua888.com/upload/2018/04/24/577972e9fbeb0fa5e7f35f5e36f7f382.jpg"}],"difftime":"2018-04-24 11:46:05","href":"http://m.ajihua888.com/question/detail?id=59&uid=58146&type=2"}],"count":"1","pageCount":1,"hasmore":0}]
     * message : 操作成功
     */

    private String code;
    private String message;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * list : [{"id":"59","name":"errrrt","uid":"58146","gradeid":"1","subjectid":"1","answernum":"14","createdate":"2018-04-24 11:46:05","point":"0","userInfo":{"uid":"58146","nickname":"wudi","avatar":"http://img.ajihua888.com/upload/2018/04/04/dcc98c324857a0ab2e5cf13bc0f5cc87.jpg"},"grade":{"id":"1","name":"小学一年级"},"subject":{"Id":"1","Name":"数学"},"images":[{"id":"458","resourceid":"59","url":"http://img.ajihua888.com/upload/2018/04/24/577972e9fbeb0fa5e7f35f5e36f7f382.jpg"}],"difftime":"2018-04-24 11:46:05","href":"http://m.ajihua888.com/question/detail?id=59&uid=58146&type=2"}]
         * count : 1
         * pageCount : 1
         * hasmore : 0
         */

        private String count;
        private int pageCount;
        private int hasmore;
        private List<ListBean> list;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getHasmore() {
            return hasmore;
        }

        public void setHasmore(int hasmore) {
            this.hasmore = hasmore;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 59
             * name : errrrt
             * uid : 58146
             * gradeid : 1
             * subjectid : 1
             * answernum : 14
             * createdate : 2018-04-24 11:46:05
             * point : 0
             * userInfo : {"uid":"58146","nickname":"wudi","avatar":"http://img.ajihua888.com/upload/2018/04/04/dcc98c324857a0ab2e5cf13bc0f5cc87.jpg"}
             * grade : {"id":"1","name":"小学一年级"}
             * subject : {"Id":"1","Name":"数学"}
             * images : [{"id":"458","resourceid":"59","url":"http://img.ajihua888.com/upload/2018/04/24/577972e9fbeb0fa5e7f35f5e36f7f382.jpg"}]
             * difftime : 2018-04-24 11:46:05
             * href : http://m.ajihua888.com/question/detail?id=59&uid=58146&type=2
             */

            private String id;
            private String name;
            private String uid;
            private String gradeid;
            private String subjectid;
            private String answernum;
            private String createdate;
            private String point;
            private UserInfoBean userInfo;
            private GradeBean grade;
            private SubjectBean subject;
            private String difftime;
            private String href;
            private List<ImagesBean> images;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getGradeid() {
                return gradeid;
            }

            public void setGradeid(String gradeid) {
                this.gradeid = gradeid;
            }

            public String getSubjectid() {
                return subjectid;
            }

            public void setSubjectid(String subjectid) {
                this.subjectid = subjectid;
            }

            public String getAnswernum() {
                return answernum;
            }

            public void setAnswernum(String answernum) {
                this.answernum = answernum;
            }

            public String getCreatedate() {
                return createdate;
            }

            public void setCreatedate(String createdate) {
                this.createdate = createdate;
            }

            public String getPoint() {
                return point;
            }

            public void setPoint(String point) {
                this.point = point;
            }

            public UserInfoBean getUserInfo() {
//                if (userInfo == null) {
//                    userInfo = new UserInfoBean();
//                }
                return userInfo;
            }

            public void setUserInfo(UserInfoBean userInfo) {
                this.userInfo = userInfo;
            }

            public GradeBean getGrade() {
                return grade;
            }

            public void setGrade(GradeBean grade) {
                this.grade = grade;
            }

            public SubjectBean getSubject() {
                return subject;
            }

            public void setSubject(SubjectBean subject) {
                this.subject = subject;
            }

            public String getDifftime() {
                return difftime;
            }

            public void setDifftime(String difftime) {
                this.difftime = difftime;
            }

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }

            public List<ImagesBean> getImages() {
                return images;
            }

            public void setImages(List<ImagesBean> images) {
                this.images = images;
            }

            public static class UserInfoBean {
                /**
                 * uid : 58146
                 * nickname : wudi
                 * avatar : http://img.ajihua888.com/upload/2018/04/04/dcc98c324857a0ab2e5cf13bc0f5cc87.jpg
                 */

                private String uid;
                private String nickname;
                private String avatar;

                public String getUid() {
                    return uid;
                }

                public void setUid(String uid) {
                    this.uid = uid;
                }

                public String getNickname() {
                    return nickname;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }

                public String getAvatar() {
                    return avatar;
                }

                public void setAvatar(String avatar) {
                    this.avatar = avatar;
                }
            }

            public static class GradeBean {
                /**
                 * id : 1
                 * name : 小学一年级
                 */

                private String id;
                private String name;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class SubjectBean {
                /**
                 * Id : 1
                 * Name : 数学
                 */

                private String Id;
                private String Name;

                public String getId() {
                    return Id;
                }

                public void setId(String Id) {
                    this.Id = Id;
                }

                public String getName() {
                    return Name;
                }

                public void setName(String Name) {
                    this.Name = Name;
                }
            }

            public static class ImagesBean {
                /**
                 * id : 458
                 * resourceid : 59
                 * url : http://img.ajihua888.com/upload/2018/04/24/577972e9fbeb0fa5e7f35f5e36f7f382.jpg
                 */

                private String id;
                private String resourceid;
                private String url;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getResourceid() {
                    return resourceid;
                }

                public void setResourceid(String resourceid) {
                    this.resourceid = resourceid;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }
    }
}
