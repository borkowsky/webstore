package net.rewerk.webstore.configuration.pointer;

import net.rewerk.webstore.model.entity.User;

import java.util.HashMap;
import java.util.Map;

public class EntityViewLevelMapping {
    public static final Map<User.Role, Class<?>> MAPPING = new HashMap<>();

    static {
        MAPPING.put(User.Role.USER, ViewLevel.RoleUser.class);
        MAPPING.put(User.Role.MANAGER, ViewLevel.RoleManager.class);
        MAPPING.put(User.Role.ADMINISTRATOR, ViewLevel.RoleAdministrator.class);
    }
}
