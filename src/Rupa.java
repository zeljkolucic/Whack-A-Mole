package igra;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class Rupa extends Canvas implements Runnable {

	private Thread nit;
	private Basta basta;
	private Zivotinja zivotinja;
	private int brojKoraka;
	private int trenutniBrojKoraka;
	private Color boja = new Color(102, 51, 0);

	public Rupa(Basta basta) {
		this.basta = basta;
		setBackground(boja);
		setSize(100, 100);
	}

	public void postaviZivotinju(Zivotinja zivotinja) {
		this.zivotinja = zivotinja;
	}

	public Zivotinja dohvatiZivotinju() {
		return zivotinja;
	}

	public void zgaziRupu() {
		if (zivotinja == null)
			return;
		zivotinja.udariZivotinju();
	}

	public void postaviBrojKoraka(int br) {
		brojKoraka = br;
	}

	public int dohvatiBrojKoraka() {
		return brojKoraka;
	}

	public void stvoriNit() {
		nit = new Thread(this);
	}

	public synchronized boolean nitPokrenuta() {
		if (nit != null)
			return nit.isAlive();
		return false;
	}

	public synchronized void pokreni() {
		if (!nit.isAlive())
			nit.start();
	}

	public synchronized void zaustavi() {
		nit.interrupt();
		zivotinja = null;
		repaint();
	}

	@Override
	public void run() {
		trenutniBrojKoraka = 0;
		while (!Thread.interrupted()) {
			try {
				++trenutniBrojKoraka;
				repaint();
				Thread.sleep(100);
				if (trenutniBrojKoraka == brojKoraka) {
					Thread.sleep(2000);
					if (zivotinja.pobeglaZivotinja())
						basta.smanjiKolicinuPovrca();
					zaustavi();
				}
			} catch (InterruptedException e) {
				nit.interrupt();
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		if (zivotinja != null) {
			int korak = getWidth() / brojKoraka;
			int sirinaPlatna = getWidth();
			int visinaPlatna = getHeight();
			int x = sirinaPlatna / 2 - korak / 2 * trenutniBrojKoraka;
			int y = visinaPlatna / 2 - korak / 2 * trenutniBrojKoraka;
			int sirina = korak * trenutniBrojKoraka;
			int visina = korak * trenutniBrojKoraka;
			zivotinja.iscrtaj(x, y, sirina, visina);
		}
	}

}
