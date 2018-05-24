package oms.mmc.android.fast.framwork.sample.bean;

import java.util.ArrayList;

/**
 * Package: oms.mmc.android.fast.framwork.sample.bean
 * FileName: ContactList
 * Date: on 2018/5/23  下午11:19
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class ContactList extends Result {
    private ArrayList<Contact> content;

    public ArrayList<Contact> getContent() {
        return content;
    }

    public void setContent(ArrayList<Contact> content) {
        this.content = content;
    }

    public static class Contact extends Base {
        private String id;
        private String name;
        private String gender;
        private int age;
        private String phone;
        private String email;
        private String qq;

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

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }
    }
}