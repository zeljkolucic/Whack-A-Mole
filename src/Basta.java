package igra;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Basta extends Panel implements Runnable {

	private Thread nit;
	private Color boja = Color.GREEN;
	private Rupa[][] rupe;
	private int kolicinaPovrca;
	private int intervalCekanja;
	private int brojKoraka;

	public Basta(int brojVrsta, int brojKolona) {
		setBackground(boja);
		setLayout(new GridLayout(brojVrsta, 1));
		rupe = new Rupa[brojVrsta][brojKolona];
		for (int i = 0; i < rupe.length; i++) {
			Panel panel = new Panel();
			for (int j = 0; j < rupe[0].length; j++) {
				int ii = i, jj = j;
				rupe[i][j] = new Rupa(this);
				panel.add(rupe[i][j]);
				rupe[i][j].addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						rupe[ii][jj].zgaziRupu();
					}
				});
			}
			add(panel);
		}		
		kolicinaPovrca = 100;
	}

	public void postaviBrojKoraka(int br) {
		brojKoraka = br;
		for (int i = 0; i < rupe.length; i++) {
			for (int j = 0; j < rupe[0].length; j++) {
				rupe[i][j].postaviBrojKoraka(br);
			}
		}
	}

	public int dohvatiBrojKoraka() {
		return brojKoraka;
	}

	public void smanjiKolicinuPovrca() {
		kolicinaPovrca--;
		Igra.dohvatiIgru().azuriraj(kolicinaPovrca);
		if (kolicinaPovrca == 0)
			zaustavi();
	}

	public void postaviIntervalCekanja(int dt) {
		this.intervalCekanja = dt;
	}

	public synchronized void pokreni() {
		kolicinaPovrca = 100;
		Igra.dohvatiIgru().azuriraj(kolicinaPovrca);
		nit = new Thread(this);
		nit.start();
	}

	public synchronized void zaustavi() {
		nit.interrupt();
		for (int i = 0; i < rupe.length; i++) {
			for (int j = 0; j < rupe[0].length; j++) {
				if (rupe[i][j].nitPokrenuta())
					rupe[i][j].zaustavi();
			}
		}		
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			try {
				Thread.sleep(intervalCekanja);
				int i = (int) (Math.random() * rupe.length);
				int j = (int) (Math.random() * rupe[0].length);
				if (!rupe[i][j].nitPokrenuta()) {
					rupe[i][j].stvoriNit();
					new Krtica(rupe[i][j]);
					rupe[i][j].pokreni();
					intervalCekanja -= 0.01 * intervalCekanja;
				}
			} catch (InterruptedException e) {
				nit.interrupt();
			}
		}

	}

}
