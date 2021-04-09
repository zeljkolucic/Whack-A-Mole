package igra;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Igra extends Frame {

	private static Igra igra;
	private Basta basta;
	private Button dugme;
	private Label povrce = new Label("Povrce: 100");
	private boolean uToku;
	private CheckboxGroup grupa = new CheckboxGroup();

	private Igra() {
		super("Igra");
		setSize(600, 500);
		setResizable(false);
		basta = new Basta(4, 4);
		igra = this;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (uToku)
					basta.zaustavi();
				dispose();
			}
		});
		popuniProzor();
		setVisible(true);
	}

	public static Igra dohvatiIgru() {
		if(igra == null) igra = new Igra();
		return igra;
	}

	public void azuriraj(int i) {
		povrce.setText("Povrce: " + i);
	}

	private void popuniProzor() {
		setLayout(new BorderLayout());
		add(basta, BorderLayout.CENTER);
		add(istocniPanel(), BorderLayout.EAST);
	}

	private Panel istocniPanel() {
		Panel p = new Panel();
		p.setLayout(new GridLayout(2, 1));
		p.add(istocniGornjiPanel());
		povrce.setFont(new Font(null, Font.BOLD, 24));
		p.add(povrce);
		return p;
	}

	private Panel istocniGornjiPanel() {
		Panel p = new Panel();
		p.setLayout(new GridLayout(4, 1));
		Label tezina = new Label("Tezina");
		tezina.setFont(new Font(null, Font.BOLD, 18));
		p.add(tezina);
		p.add(radioDugmad());
		DugmeAkcija osluskivac = new DugmeAkcija();
		dugme = new Button("Kreni");
		dugme.addActionListener(osluskivac);
		p.add(dugme);
		return p;
	}

	private Panel radioDugmad() {
		Panel p = new Panel();
		p.setLayout(new GridLayout(3, 1));
		Checkbox lako = new Checkbox("Lako", false, grupa);
		p.add(lako);
		Checkbox srednje = new Checkbox("Srednje", false, grupa);
		p.add(srednje);
		Checkbox tesko = new Checkbox("Tesko", false, grupa);
		p.add(tesko);
		return p;
	}

	private class DugmeAkcija implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!uToku) {
				Checkbox radioDugme = grupa.getSelectedCheckbox();
				if (radioDugme == null)
					return;			
				if (dugme.getLabel() == "Lako") {
					basta.postaviIntervalCekanja(1000);
					basta.postaviBrojKoraka(10);
				} else if (dugme.getLabel() == "Srednje") {
					basta.postaviIntervalCekanja(750);
					basta.postaviBrojKoraka(8);
				} else {
					basta.postaviIntervalCekanja(500);
					basta.postaviBrojKoraka(6);
				}
				basta.pokreni();
				dugme.setLabel("Stani");
			} else {
				basta.zaustavi();
				dugme.setLabel("Kreni");
			}
			uToku = !uToku;
		}
	}

	public static void main(String[] args) {
		dohvatiIgru();
	}

}
