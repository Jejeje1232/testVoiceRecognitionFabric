package mod.jeje.voicerecognition;

public class constants {
    //Word pool size (Default: 40):
    public static int N_MOST_WORDS = 40;

    //Loop time to get a new word/s (default: 5mins = 300f):
    public static float GET_WORD_TIME_SECONDS = 10f;

    //Amount of words to get:
    public static int WORD_DIFF_SCALE = 1;

    //Amount of time the notif from the events stays on screen (Not seconds, but arbitrary, 1 would be the same time that it takes to fade in):
    public static int NOTIF_TIME = 5;

    //Time separation between event executions (In seconds):
    public static int EVENT_DIST = 5;

    //An option to have every player die on one player dead.
    public static boolean ABS_DEATH = true;
}
