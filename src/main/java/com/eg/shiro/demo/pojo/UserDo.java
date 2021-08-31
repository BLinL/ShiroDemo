package com.eg.shiro.demo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.beans.Transient;
import java.util.Set;

public class UserDo {

    @NotNull(message = "accountName can't be null")
    private String accountName;
    private String username;

    @JsonIgnore
    @NotNull(message = "password can't be null")
    private String password;

    private String gender;
    private String tel;

    @Email
    private String email;
    private String salt;

    private int status;

    private Set<String> roles;
    private Set<String> permissions;

    public UserDo() {
    }

    public UserDo(Builder builder) {
        this.accountName = builder.accountName;
        this.username = builder.username;
        this.password = builder.password;
        this.gender = builder.gender;
        this.tel = builder.tel;
        this.email = builder.email;
        this.salt = builder.salt;
        this.status = builder.status;
    }

    public static class Builder{
        private String accountName;
        private String username;
        private String password;

        private String gender;
        private String tel;
        private String email;
        private String salt;
        private int status;

        public Builder(){

        }

        public Builder accountName(String accountName){
            this.accountName = accountName;
            return this;
        }

        public Builder password(String password){
            this.password = password;
            return this;
        }

        public Builder username(String username){
            this.username = username;
            return this;
        }

        public Builder gender(String gender){
            this.gender = gender;
            return this;
        }

        public Builder email(String email){
            this.email = email;
            return this;
        }

        public Builder tel(String tel){
            this.tel = tel;
            return this;
        }

        public Builder salt(String salt){
            this.salt = salt;
            return this;
        }

        public Builder status(int status){
            this.status = status;
            return this;
        }

        public UserDo build(){
            return new UserDo(this);
        }

    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "UserDo{" +
                "accountName='" + accountName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", salt='" + salt + '\'' +
                ", status='" + status + '\'' +
                ", roles=" + roles+ '\'' +
                ", permissions=" + permissions + '\'' +
                '}';
    }
}
