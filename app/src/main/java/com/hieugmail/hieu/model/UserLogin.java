package com.hieugmail.hieu.model;

import com.orm.SugarRecord;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Builder;

/**
 * Model user login
 */
@Data
@AllArgsConstructor(suppressConstructorProperties = true)
@EqualsAndHashCode(callSuper = false)
@Builder
public class UserLogin extends SugarRecord {
    String name;
    String password;

    public UserLogin() {
        // no-op
    }

    @Override
    public long save() {
        // just only save one record, so we need to clear DB before saving
        deleteAll(UserLogin.class);
        return super.save();
    }
}
