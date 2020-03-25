package cn.edu.nju.user.dao;

public interface BatchDao<T> {
    Iterable<T> batchSave(Iterable<T> users);

    Iterable<T> batchUpdate(Iterable<T> users);
}
