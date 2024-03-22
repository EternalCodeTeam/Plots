plugins {
    id("java-library")
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("xyz.jpenilla.run-paper") version "2.2.3"
}

repositories {
    gradlePluginPortal()
    mavenCentral()
    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
    maven { url = uri("https://storehouse.okaeri.eu/repository/maven-public/") }
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
    maven { url = uri("https://repo.codemc.io/repository/maven-snapshots/") }
    maven { url = uri("https://repo.panda-lang.org/releases") }
    maven { url = uri("https://repo.eternalcode.pl/releases") }
    maven { url = uri("https://jitpack.io") }
}

version = "1.0.0-BETA"
group = "com.eternalcode"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    // spigot + paper lib
    compileOnly("org.spigotmc:spigot-api:1.19.1-R0.1-SNAPSHOT")
    api("io.papermc:paperlib:1.0.8")


    // anvil gui
    implementation("net.wesjd:anvilgui:1.9.0-SNAPSHOT")

    // adventure
    compileOnly("net.kyori:adventure-platform-bukkit:4.3.2")
    compileOnly("net.kyori:adventure-text-minimessage:4.16.0")

    // LiteCommands
    implementation("dev.rollczi.litecommands:bukkit:2.8.9")
    implementation("dev.rollczi.litecommands:core:2.8.9")

    // panda-dzikoysk crafted libs
    implementation("org.panda-lang:expressible:1.3.6")
    implementation("org.panda-lang:panda-utilities:0.5.3-alpha")

    // TriumphGui
    implementation("dev.triumphteam:triumph-gui:3.1.7")

    // okaeri configs
    implementation("eu.okaeri:okaeri-configs-yaml-snakeyaml:4.0.9")

    // ormlite jdbc
    compileOnly("com.j256.ormlite:ormlite-jdbc:6.1")

    // hikari
    compileOnly("com.zaxxer:HikariCP:5.1.0")

    // bStats
    implementation("org.bstats:bstats-bukkit:3.0.2")

    // vault
    compileOnly("com.github.MilkBowl:VaultAPI:1.7.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")
}

bukkit {
    main = "com.eternalcode.plots.EternalPlots"
    apiVersion = "1.17"
    website = "https://eternalcode.pl"
    prefix = "EternalPlots"
    author = "EternalCodeTeam"
    name = "EternalPlots"
    description = "Plots plugin for your server!"
    version = "${project.version}"

    libraries = listOf(
        "org.postgresql:postgresql:42.7.3",
        "com.h2database:h2:2.2.224",
        "com.j256.ormlite:ormlite-jdbc:6.1",
        "com.zaxxer:HikariCP:5.1.0",
        "net.kyori:adventure-platform-bukkit:4.3.2",
        "net.kyori:adventure-text-minimessage:4.16.0",
        "org.mariadb.jdbc:mariadb-java-client:3.3.3"
    )

    depend = listOf(
        "Vault"
    )
}



tasks {
    runServer {
        minecraftVersion("1.18.2")
    }

    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        archiveFileName.set("EternalPlots v${project.version}.jar")

        exclude("org/intellij/lang/annotations/**","org/jetbrains/annotations/**","org/checkerframework/**","META-INF/**","javax/**")

        val prefix = "com.eternalcode.plots.libs"
        listOf(
                "net.dzikoysk",
                "dev.rollczi",
                "org.bstats",
                "org.panda_lang",
                "panda",
                "io.papermc.lib",
                "eu.okaeri",
                "org.checkerframework",
                "org.yaml",
                "com.github",
                "net.wesjd",
                "org.bstats",
                "dev.triumphteam.gui",
                "com.google.gson"
        ).forEach { pack ->
            relocate(pack, "$prefix.$pack")
        }
    }

    getByName<Test>("test") {
        useJUnitPlatform()
    }

}
