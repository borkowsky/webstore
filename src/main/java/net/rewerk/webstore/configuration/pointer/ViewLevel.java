package net.rewerk.webstore.configuration.pointer;

public final class ViewLevel {
    public static class RoleAnonymous {}
    public static class RoleUser extends RoleAnonymous {}
    public static class RoleManager extends RoleUser {}
    public static class RoleAdministrator extends RoleManager {}
}
