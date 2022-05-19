/* Class that defines static method of playing music in the game */

import java.util.*;
import java.text.*;
import java.io.*;

import java.net.*;
import javax.sound.sampled.*;

public class Music {

    public static final String YELLOW_BACKGROUND = "\033[43m";   // Yellow
    public static final String ANSI_RESET = "\u001B[0m";
    public static String os = System.getProperty("os.name");

    public static boolean isWindows(String os)
    {
        return os.startsWith("Windows");
    }

    public static boolean isUnix(String os) {
        return os.startsWith("Linux");
    }

    public static void playSound (String filename) {
        int BUFFER_SIZE = 128000;
        //File soundFile = null;
        AudioInputStream audioStream = null;
        AudioFormat audioFormat = null;
        SourceDataLine sourceLine = null;
        try {
            audioStream = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream ( filename )));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }

        audioFormat = audioStream.getFormat();

        DataLine.Info info = new DataLine.Info
                (
                        SourceDataLine.class,
                        audioFormat
                );
        try
        {
            sourceLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceLine.open(audioFormat);
        }
        catch (LineUnavailableException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }

        sourceLine.start();

        int nBytesRead = 0;
        byte[] abData = new byte[BUFFER_SIZE];
        while (nBytesRead != -1)
        {
            try
            {
                nBytesRead =
                        audioStream.read(abData, 0, abData.length);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            if (nBytesRead >= 0)
            {
                @SuppressWarnings("unused")
                int nBytesWritten =
                        sourceLine.write(abData, 0, nBytesRead);
            }
        }

        sourceLine.drain();
        sourceLine.close();
    }


    public static void play_welcome_music() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if(isWindows(os)){
            String filename = "files/Intro1.wav";
            File file = new File(filename);
            AudioInputStream am;
            am = AudioSystem.getAudioInputStream(file);
            AudioFormat af = am.getFormat();
            SourceDataLine sd;
            sd = AudioSystem.getSourceDataLine(af);
            sd.open();
            sd.start();
            int sumByteRead = 0;
            byte[] b = new byte[320];
            while (sumByteRead != -1) {
                sumByteRead = am.read(b, 0, b.length);
                if (sumByteRead >= 0) {
                    sd.write(b, 0, b.length);
                }
            }
        }

        if(isUnix(os)){
            playSound ( "files/Intro1.wav" );
        }

    }

    public static void play_potion_music(String name) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        play_glass_open_music();
        System.out.println(YELLOW_BACKGROUND + "..........." + name + " is drinking a Potion........." + ANSI_RESET + "\n");
        play_drinking_music();
        play_glass_close_music();
    }

    public static void play_drinking_music() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if(isWindows(os)){
            String filename = "files/Drink.wav";
            File file = new File(filename);
            AudioInputStream am;
            am = AudioSystem.getAudioInputStream(file);
            AudioFormat af = am.getFormat();
            SourceDataLine sd;
            sd = AudioSystem.getSourceDataLine(af);
            sd.open();
            sd.start();
            int sumByteRead = 0;
            byte[] b = new byte[320];
            while (sumByteRead != -1) {
                sumByteRead = am.read(b, 0, b.length);
                if (sumByteRead >= 0) {
                    sd.write(b, 0, b.length);
                }
            }
        }

        if(isUnix(os)){
            playSound ( "files/Drink.wav" );
        }

    }

    public static void play_glass_open_music() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if(isWindows(os)){
            String filename = "files/Glass_Open.wav";
            File file = new File(filename);
            AudioInputStream am;
            am = AudioSystem.getAudioInputStream(file);
            AudioFormat af = am.getFormat();
            SourceDataLine sd;
            sd = AudioSystem.getSourceDataLine(af);
            sd.open();
            sd.start();
            int sumByteRead = 0;
            byte[] b = new byte[320];
            while (sumByteRead != -1) {
                sumByteRead = am.read(b, 0, b.length);
                if (sumByteRead >= 0) {
                    sd.write(b, 0, b.length);
                }
            }
        }

        if(isUnix(os)){
            playSound ( "files/Glass_Open.wav" );
        }

    }

    public static void play_glass_close_music() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if(isWindows(os)){
            String filename = "files/Glass_Close.wav";
            File file = new File(filename);
            AudioInputStream am;
            am = AudioSystem.getAudioInputStream(file);
            AudioFormat af = am.getFormat();
            SourceDataLine sd;
            sd = AudioSystem.getSourceDataLine(af);
            sd.open();
            sd.start();
            int sumByteRead = 0;
            byte[] b = new byte[320];
            while (sumByteRead != -1) {
                sumByteRead = am.read(b, 0, b.length);
                if (sumByteRead >= 0) {
                    sd.write(b, 0, b.length);
                }
            }
        }

        if(isUnix(os)){
            playSound("files/Glass_Close.wav");
        }


    }

    public static void play_cash_music() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if(isWindows(os)){
            String filename = "files/Cash.wav";
            File file = new File(filename);
            AudioInputStream am;
            am = AudioSystem.getAudioInputStream(file);
            AudioFormat af = am.getFormat();
            SourceDataLine sd;
            sd = AudioSystem.getSourceDataLine(af);
            sd.open();
            sd.start();
            int sumByteRead = 0;
            byte[] b = new byte[320];
            while (sumByteRead != -1) {
                sumByteRead = am.read(b, 0, b.length);
                if (sumByteRead >= 0) {
                    sd.write(b, 0, b.length);
                }
            }
        }

        if(isUnix(os)){
            playSound("files/Cash.wav");
        }

    }

    public static void play_spell_music() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if(isWindows(os)){
            String filename = "files/Attack_Lightningspell.wav";
            File file = new File(filename);
            AudioInputStream am;
            am = AudioSystem.getAudioInputStream(file);
            AudioFormat af = am.getFormat();
            SourceDataLine sd;
            sd = AudioSystem.getSourceDataLine(af);
            sd.open();
            sd.start();
            int sumByteRead = 0;
            byte[] b = new byte[320];
            while (sumByteRead != -1) {
                sumByteRead = am.read(b, 0, b.length);
                if (sumByteRead >= 0) {
                    sd.write(b, 0, b.length);
                }
            }
        }

        if(isUnix(os)){
            playSound("files/Attack_Lightningspell.wav");
        }

    }

    public static void play_weapon_attack_music() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if(isWindows(os)){
            String filename = "files/Attack_Weapon.wav";
            File file = new File(filename);
            AudioInputStream am;
            am = AudioSystem.getAudioInputStream(file);
            AudioFormat af = am.getFormat();
            SourceDataLine sd;
            sd = AudioSystem.getSourceDataLine(af);
            sd.open();
            sd.start();
            int sumByteRead = 0;
            byte[] b = new byte[320];
            while (sumByteRead != -1) {
                sumByteRead = am.read(b, 0, b.length);
                if (sumByteRead >= 0) {
                    sd.write(b, 0, b.length);
                }
            }
        }

        if(isUnix(os)){
            playSound("files/Attack_Weapon.wav");
        }

    }

    public static void play_monster_attack_music() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if(isWindows(os)){
            String filename = "files/Attack_Weapon.wav";
            File file = new File(filename);
            AudioInputStream am;
            am = AudioSystem.getAudioInputStream(file);
            AudioFormat af = am.getFormat();
            SourceDataLine sd;
            sd = AudioSystem.getSourceDataLine(af);
            sd.open();
            sd.start();
            int sumByteRead = 0;
            byte[] b = new byte[320];
            while (sumByteRead != -1) {
                sumByteRead = am.read(b, 0, b.length);
                if (sumByteRead >= 0) {
                    sd.write(b, 0, b.length);
                }
            }
        }

        if(isUnix(os)){
            playSound("files/Attack_Weapon.wav");
        }
    }

    public static void play_moving_music(String name) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if(isWindows(os)){
            String filename = "files/Hero_Move.wav";
            File file = new File(filename);
            AudioInputStream am;
            am = AudioSystem.getAudioInputStream(file);
            AudioFormat af = am.getFormat();
            SourceDataLine sd;
            sd = AudioSystem.getSourceDataLine(af);
            sd.open();
            sd.start();
            int sumByteRead = 0;
            byte[] b = new byte[320];
            int flag = 0;
            while (sumByteRead != -1) {
                if (flag==0){
                    System.out.println(YELLOW_BACKGROUND + "..........." + name + " is Moving........." + ANSI_RESET);
                    flag=1;
                }
                sumByteRead = am.read(b, 0, b.length);
                if (sumByteRead >= 0) {
                    sd.write(b, 0, b.length);
                }
            }
        }

        if(isUnix(os)){
            System.out.println(YELLOW_BACKGROUND + "..........." + name + " is Moving........." + ANSI_RESET);
            playSound("files/Hero_Move.wav");
        }

    }

    public static void play_change_armor_music(String name) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if(isWindows(os)){
            String filename = "files/Change_Armour.wav";
            File file = new File(filename);
            AudioInputStream am;
            am = AudioSystem.getAudioInputStream(file);
            AudioFormat af = am.getFormat();
            SourceDataLine sd;
            sd = AudioSystem.getSourceDataLine(af);
            sd.open();
            sd.start();
            int sumByteRead = 0;
            byte[] b = new byte[320];
            int flag = 0;
            while (sumByteRead != -1) {
                if (flag==0){
                    System.out.println(YELLOW_BACKGROUND + "..........." + name + " is changing his Armour........." + ANSI_RESET);
                    flag=1;
                }
                sumByteRead = am.read(b, 0, b.length);
                if (sumByteRead >= 0) {
                    sd.write(b, 0, b.length);
                }
            }
        }

        if(isUnix(os)){
            System.out.println(YELLOW_BACKGROUND + "..........." + name + " is changing his Armour........." + ANSI_RESET);
            playSound("files/Change_Armour.wav");
        }
    }

    public static void play_teleport_music(String name) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if(isWindows(os)){
            String filename = "files/Teleport.wav";
            File file = new File(filename);
            AudioInputStream am;
            am = AudioSystem.getAudioInputStream(file);
            AudioFormat af = am.getFormat();
            SourceDataLine sd;
            sd = AudioSystem.getSourceDataLine(af);
            sd.open();
            sd.start();
            int sumByteRead = 0;
            byte[] b = new byte[320];
            int flag = 0;
            while (sumByteRead != -1) {
                if (flag==0){
                    System.out.println(YELLOW_BACKGROUND + "..........." + name + " is Teleporting........." + ANSI_RESET);
                    flag=1;
                }
                sumByteRead = am.read(b, 0, b.length);
                if (sumByteRead >= 0) {
                    sd.write(b, 0, b.length);
                }
            }
        }

        if(isUnix(os)){
            System.out.println(YELLOW_BACKGROUND + "..........." + name + " is Teleporting........." + ANSI_RESET);
            playSound("files/Teleport.wav");
        }

    }

    public static void play_change_weapon_music(String name) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if(isWindows(os)){
            String filename = "files/Change_Armour.wav";
            File file = new File(filename);
            AudioInputStream am;
            am = AudioSystem.getAudioInputStream(file);
            AudioFormat af = am.getFormat();
            SourceDataLine sd;
            sd = AudioSystem.getSourceDataLine(af);
            sd.open();
            sd.start();
            int sumByteRead = 0;
            byte[] b = new byte[320];
            int flag = 0;
            while (sumByteRead != -1) {
                if (flag==0){
                    System.out.println(YELLOW_BACKGROUND + "..........." + name + " is changing his Weapon........." + ANSI_RESET);
                    flag=1;
                }
                sumByteRead = am.read(b, 0, b.length);
                if (sumByteRead >= 0) {
                    sd.write(b, 0, b.length);
                }
            }
        }

        if(isUnix(os)){
            System.out.println(YELLOW_BACKGROUND + "..........." + name + " is changing his Weapon........." + ANSI_RESET);
            playSound("files/Change_Armour.wav");
        }
    }

    public static void play_hero_win_music() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if(isWindows(os)){
            String filename = "files/Heroes_Win.wav";
            File file = new File(filename);
            AudioInputStream am;
            am = AudioSystem.getAudioInputStream(file);
            AudioFormat af = am.getFormat();
            SourceDataLine sd;
            sd = AudioSystem.getSourceDataLine(af);
            sd.open();
            sd.start();
            int sumByteRead = 0;
            byte[] b = new byte[320];
            while (sumByteRead != -1) {
                sumByteRead = am.read(b, 0, b.length);
                if (sumByteRead >= 0) {
                    sd.write(b, 0, b.length);
                }
            }
        }

        if(isUnix(os)){
            playSound("files/Heroes_Win.wav");
        }

    }

    public static void play_monsters_win_music() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if(isWindows(os)){
            String filename = "files/Monsters_Win.wav";
            File file = new File(filename);
            AudioInputStream am;
            am = AudioSystem.getAudioInputStream(file);
            AudioFormat af = am.getFormat();
            SourceDataLine sd;
            sd = AudioSystem.getSourceDataLine(af);
            sd.open();
            sd.start();
            int sumByteRead = 0;
            byte[] b = new byte[320];
            while (sumByteRead != -1) {
                sumByteRead = am.read(b, 0, b.length);
                if (sumByteRead >= 0) {
                    sd.write(b, 0, b.length);
                }
            }
        }

        if(isUnix(os)){
            playSound("files/Monsters_Win.wav");
        }
    }
}
