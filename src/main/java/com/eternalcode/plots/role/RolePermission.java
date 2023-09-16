package com.eternalcode.plots.role;

public enum RolePermission {

    // TODO: To jest takie poglądowe, trzeba będzie to jeszcze przemyśleć, jakie faktycznie permisje mogą być
    OWNER,
    KICK_PLAYER,
    MANAGE_PLAYER_PERMISSIONS,
    CHEST_ACCESS,
    BUILD,
    BREAK,
    INTERACT,
    USE,
    ATTACK,
    PLACE,
    PICKUP,
    DROP,
    MOVE,
    SLEEP,
    SPAWN,
    TELEPORT,


/*
ranks:
  owner:
    permissions: FULL
  moderator:
    permissions: KICK_PLAYER, MANAGE_PLAYER_PERMISSIONS
    extend: member
  member:
    permissions: BUILD, BREAK, INTERACT, USE, ATTACK, PLACE, PICKUP, DROP, MOVE, SLEEP, SPAWN, TELEPORT
    parent: guest
  guest:
    permissions: OPEN, INTERACT, SLEEP, PICKUP, DROP

 */

}
