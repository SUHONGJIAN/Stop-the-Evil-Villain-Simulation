package edu.nyu.cs9053.homework6;

/**
 * User: blangel
 * Date: 10/11/17
 * Time: 8:03 AM
 */
public class BombSquadTechnicianRecruiter {

    private static final BombSquadProfessionalTechnician professional = new BombSquadProfessionalTechnician();

    public static BombSquadTechnician recruit() {
        return professional;
    }

}
