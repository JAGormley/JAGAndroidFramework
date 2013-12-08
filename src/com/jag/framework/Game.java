package com.jag.framework;

import com.jag.framework.implementation.AndroidFastRenderView;

public interface Game {

    public Audio getAudio();

    public Input getInput();

    public FileIO getFileIO();

    public Graphics getGraphics();

    public void setScreen(Screen screen);

    public Screen getCurrentScreen();

    public Screen getInitScreen();
    
    public int getLoadHeight();
    
    public int getLoadWidth();
    
}
