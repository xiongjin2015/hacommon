
package com.haha.common.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class TestEntity implements Parcelable {

    private int id;
    private String name;
    private int age;

    public TestEntity(Parcel in) {
        id = in.readInt();
        name = in.readString();
        age = in.readInt();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(age);
    }

    public static final Parcelable.Creator<TestEntity> CREATOR = new Parcelable.Creator<TestEntity>() {

        @Override
        public TestEntity createFromParcel(Parcel source) {
            TestEntity entity = new TestEntity(source);
            return entity;
        }

        @Override
        public TestEntity[] newArray(int size) {
            return new TestEntity[size];
        }
    };

}
