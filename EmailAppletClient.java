package edu.ucm.cs.lab2;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

/* $Id: MailClient.java,v 1.7 1999/07/22 12:07:30 kangasha Exp $ */

/**
 * A simple mail client with a GUI for sending mail.
 *
 * @author Jussi Kangasharju
 */
public class EmailAppletClient extends Frame {

	private static final long serialVersionUID = 1L;
	/* The stuff for the GUI. */
	private Button btSend = new Button("Send");
	private Button btClear = new Button("Clear");
	private Button btQuit = new Button("Quit");
	private Label serverLabel = new Label("Local mailserver:");
	private TextField serverField = new TextField("", 40);
	private Label fromLabel = new Label("From:");
	private TextField fromField = new TextField("", 40);
	private Label toLabel = new Label("To:\t\t\t"); 
	private TextField toField = new TextField("", 40);
	private Label ccLabel = new Label("Cc:\t\t\t"); 
	private TextField ccField = new TextField("", 40);
	private Label bccLabel = new Label("Bcc:\t\t\t"); 
	private TextField bccField = new TextField("", 40);
	private Label subjectLabel = new Label("Subject:");
	private TextField subjectField = new TextField("", 40);
	private Label messageLabel = new Label("Message:");
	private TextArea messageText = new TextArea(10, 40);

	/**
	 * Create a new MailClient window with fields for entering all
	 * the relevant information (From, To, Subject, and message).
	 */
	public EmailAppletClient() {
		super("Java Mailclient");

		/* Create panels for holding the fields. 
		 * To make it look nice,create an extra panel for 
		 * holding all the child panels. */

		// Panel to hold server fields
		Panel serverPanel = new Panel(new BorderLayout());
		serverPanel.add(serverLabel,BorderLayout.WEST);
		serverPanel.add(serverField, BorderLayout.EAST);
		// Panel to hold from fields
		Panel fromPanel = new Panel(new BorderLayout());
		fromPanel.add(fromLabel, BorderLayout.WEST);
		fromPanel.add(fromField, BorderLayout.CENTER);
		// Panel to hold To fields
		Panel toPanel = new Panel(new BorderLayout());
		toPanel.add(toLabel, BorderLayout.WEST);
		toPanel.add(toField, BorderLayout.CENTER);
		// Panel to hold CC fields
		Panel ccPanel = new Panel(new BorderLayout());
		ccPanel.add(ccLabel, BorderLayout.WEST);
		ccPanel.add(ccField, BorderLayout.CENTER);
		// Panel to hold CC fields
		Panel bccPanel = new Panel(new BorderLayout());
		bccPanel.add(bccLabel, BorderLayout.WEST);
		bccPanel.add(bccField, BorderLayout.CENTER);
		// Panel to hold Subject fields
		Panel subjectPanel = new Panel(new BorderLayout());
		subjectPanel.add(subjectLabel, BorderLayout.WEST);
		subjectPanel.add(subjectField, BorderLayout.CENTER);
		// Panel to hold message body text area
		Panel messagePanel = new Panel(new BorderLayout());
		messagePanel.add(messageLabel, BorderLayout.NORTH);	
		messagePanel.add(messageText, BorderLayout.CENTER);
		Panel fieldPanel = new Panel(new GridLayout(0, 1));
		fieldPanel.add(serverPanel);
		fieldPanel.add(fromPanel);
		fieldPanel.add(toPanel);
		fieldPanel.add(ccPanel);
		fieldPanel.add(bccPanel);
		fieldPanel.add(subjectPanel);

		/* Create a panel for the
		 * buttons and add listeners to the buttons. */
		Panel buttonPanel = new Panel(new GridLayout(1, 0));
		btSend.addActionListener(new SendListener());
		btClear.addActionListener(new ClearListener());
		btQuit.addActionListener(new QuitListener());
		buttonPanel.add(btSend);
		buttonPanel.add(btClear);
		buttonPanel.add(btQuit);

		/* Add, pack, and show. */
		add(fieldPanel, BorderLayout.NORTH);
		add(messagePanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		pack();
		// show(); As this is deprecated method we use setVisible(true) to show window.
		setVisible(true);
	}

	static public void main(String argv[]) {
		new EmailAppletClient();
	}

	/* Handler for the Send-button. */
	class SendListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("Sending mail");

			/* Check that we have the local mailserver */
			if ((serverField.getText()).equals("")) {
				System.out.println("Local mail server name cannot be empty");
				return;
			}

			/* Check that we have the sender and recipient. */
			if((fromField.getText()).equals("")) {
				System.out.println("Sender email is required");
				return;
			}
			if((toField.getText()).equals("")) {
				System.out.println("Recipient email is required");
				return;
			}

			String toEmails = toField.getText();
			String  ccEmails = ccField.getText();
			String  bccEmails = bccField.getText();
			/* Create the message */
			MessageBuilder mailMessage = new MessageBuilder(fromField.getText(),toEmails,ccEmails,bccEmails,
					subjectField.getText(),messageText.getText());

			/* Check that the message is valid, i.e., sender and
	       recipient addresses look ok. */
			if(!mailMessage.isValid()) {
				return;
			}

			/* Create the envelope, open the connection and try to send
	       the message. */
			try {
				Envelope envelope = new Envelope(mailMessage,serverField.getText());
				JavaMailAPI.send(envelope);
			} catch (UnknownHostException e) {
				/* If there is an error, do not go further */
				return;
			} catch (IOException error) {
				System.out.println("Sending failed: " + error);
				return;
			}
			System.out.println("Mail sent succesfully from java mail!");
		}
	}

	/* Clear the fields on the GUI. */
	class ClearListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Clearing fields");
			fromField.setText("");
			toField.setText("");
			subjectField.setText("");
			messageText.setText("");
		}
	}

	/* Quit. */
	class QuitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
}

