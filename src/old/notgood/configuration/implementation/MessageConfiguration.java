package com.eternalcode.plots.notgood.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import org.bukkit.Material;

import java.util.List;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfiguration extends OkaeriConfig {

    public Warnings warnings = new Warnings();
    public Help help = new Help();
    public PlotCreation plotCreation = new PlotCreation();
    public Commands commands = new Commands();

    public static class Warnings extends OkaeriConfig {
        public String noPermission = "&cNie masz uprawnień do tej komendy! &7({PERMISSION})";
    }

    public static class Help extends OkaeriConfig {
        public List<String> message = List.of();
    }

    public static class PlotCreation extends OkaeriConfig {

        public String plotDetected = "&cW tym miejscu znajduje się inna działka";
        public String plotCreated = "&aPomyślnie utworzono działkę o nazwie {NAME}";
        public String hasLimit = "&cOsiągnięto limit działek";
        public String unknownError = "&cWystąpił błąd podczas tworzenia działki, skontaktuj się z administratorem";

        public changeNameInventory changeNameInventory = new changeNameInventory();

        public static class changeNameInventory extends OkaeriConfig {
            public Material guiItem = Material.NAME_TAG;
            public String guiTitle = "&aNazwij swoją działkę";
            public String nameExists = "&cNazwa w użyciu";
            public String nameChanged = "&aZmieniono nazwę działki na {NEW_NAME}";
            public String illegalCharacters = "&cNieprawidłowe znaki";
            public String nameTooLongOrShort = "&cNazwa jest za długa bądź za krótka";
            public String closed = "&cZamknięto okno zmiany nazwy działki, nazwa zostaje taka sama.";

        }
    }

    public static class Commands extends OkaeriConfig {
        public String needPlot = "&cMusisz podać działkę ponieważ należysz do więcej niż jednej";
        public String notOwner = "&cNie jesteś właścicielem {PLOT_NAME}";
        public String isAlreadyMember = "&cGracz {PLAYER} jest już członkiem {PLOT_NAME}";
        public String isAlreadyMember2 = "&cJesteś już członkiem {PLOT_NAME}";
        public String notMember = "&cGracz {PLAYER} nie jest członkiem {PLOT_NAME}";
        public String kickOwner = "&cNie możesz wyrzucić sam siebie";
        public String hasAlreadyInvite = "&cGracz {PLAYER} ma już zaproszenie do {PLOT_NAME}";
        public String noInvite = "&cNie posiadasz zaproszenia do {PLOT_NAME}";
        public String successInvite = "&aPomyślnie zaproszono gracza {PLAYER} do {PLOT_NAME}";
        public String successKick = "&aPomyślnie wyrzucono gracza {PLAYER} z {PLOT_NAME}";
        public String successAcceptInvite = "&aPomyślnie dołączono do {PLOT_NAME}";
        public String successRejectInvite = "&cOdrzucono zaproszenie do {PLOT_NAME}";
        public String showBorder = "&aOd teraz widzisz granice działek";
        public String hideBorder = "&cOd teraz nie widzisz granic działek";
    }

}
