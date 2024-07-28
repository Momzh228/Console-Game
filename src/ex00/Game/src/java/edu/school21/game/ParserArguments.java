package edu.school21.game;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.validators.PositiveInteger;

@Parameters(separators = "=")
public class ParserArguments {
    private final boolean isProduction;
    @Parameter(names = "--enemiesCount", required = true, validateWith = PositiveInteger.class)
    private int enemiesCount;
    @Parameter(names = "--wallsCount", required = true, validateWith = PositiveInteger.class)
    private int wallsCount;
    @Parameter(names = "--size", required = true, validateWith = PositiveInteger.class)
    private int sizeField;
    @Parameter(names = "--profile", required = true)
    private String profile;

    public ParserArguments(String[] args) throws IllegalParametersException {
        try {
            JCommander.newBuilder().addObject(this).build().parse(args);
        } catch (ParameterException e) {
            throw new IllegalParametersException(e.getMessage());
        }
        if (!profile.equals("production") && !profile.equals("dev")) {
            throw new IllegalParametersException("Illegal profile");
        }
        if (sizeField < 2) {
            throw new IllegalParametersException("Size should be greater than 2");
        }
        if (sizeField * 2 < 2 + enemiesCount + wallsCount) {
            throw new IllegalParametersException("Game size should be greater");
        }
        isProduction = profile.equals("production");
    }

    public int getEnemiesCount() {
        return enemiesCount;
    }

    public int getWallsCount() {
        return wallsCount;
    }

    public int getSizeField() {
        return sizeField;
    }

    public boolean getIsProduction() {
        return isProduction;
    }

}
