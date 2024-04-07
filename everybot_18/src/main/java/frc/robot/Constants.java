package frc.robot;

public class Constants {

    public static class DriveConstants {

        public static int LEFT_LEADER_ID = 1;
        public static int LEFT_FOLLOWER_ID = 2;
        public static int RIGHT_LEADER_ID = 3;
        public static int RIGHT_FOLLOWER_ID = 4;

        public static boolean INVERT_TURN = false;
        public static boolean INVERT_RIGHT = false;
        public static boolean INVERT_LEFT = false;

    }

    public static class ArmConstants {

        public static int CAN_ID = 5;

        public static int LOWER_LIMIT_PORT = 0;
        public static int UPPER_LIMIT_PORT = 1;

        public static double UP_POWER = 0.6;
        public static double DOWN_POWER = -0.4;

        public static double HOLD_DOWN_POWER = -0.2;

    }

    public static class IntakeConstants {

        public static int LEFT_ID = 6;
        public static int RIGHT_ID = 7;

        public static double INTAKE_POWER = 0.5;
        public static double SCORE_POWER = -0.5;

        public static double IDLE_POWER = 0.2;

    }

    public static class OIConstants {

        public static int CONTROLLER_PORT = 0;

    }
    
}
