package io.notcute.ui.awt;

import io.notcute.app.awt.AWTPlatform;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public final class Desktop {

    private Desktop() {
        throw new UnsupportedOperationException();
    }

    public static boolean open(File file) {
        if (file == null) return false;
        if (AWTPlatform.isX11) {
            try {
                new ProcessBuilder("xdg-open", file.getAbsolutePath()).inheritIO().start();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (AWTPlatform.isMac) {
            try {
                new ProcessBuilder("open", file.getAbsolutePath()).inheritIO().start();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (java.awt.Desktop.isDesktopSupported()) {
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
            if (desktop.isSupported(java.awt.Desktop.Action.OPEN)) {
                invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            desktop.open(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                return true;
            }
        }
        return false;
    }

    public static boolean browse(URI uri) {
        if (uri == null) return false;
        if (AWTPlatform.isX11) {
            try {
                new ProcessBuilder("xdg-open", uri.toString()).inheritIO().start();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (AWTPlatform.isMac) {
            try {
                new ProcessBuilder("open", uri.toString()).inheritIO().start();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (java.awt.Desktop.isDesktopSupported()) {
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
            if (desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
                invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            desktop.browse(uri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                return true;
            }
        }
        return false;
    }

    public static boolean mail(URI mailtoURI) {
        if (mailtoURI == null) return false;
        if (AWTPlatform.isX11) {
            try {
                new ProcessBuilder("xdg-open", mailtoURI.toString()).inheritIO().start();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (AWTPlatform.isMac) {
            try {
                new ProcessBuilder("open", mailtoURI.toString()).inheritIO().start();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (java.awt.Desktop.isDesktopSupported()) {
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
            if (desktop.isSupported(java.awt.Desktop.Action.MAIL)) {
                invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            desktop.mail(mailtoURI);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                return true;
            }
        }
        return false;
    }

    private static void invokeLater(Runnable runnable) {
        if (EventQueue.isDispatchThread()) runnable.run();
        else EventQueue.invokeLater(runnable);
    }

}
