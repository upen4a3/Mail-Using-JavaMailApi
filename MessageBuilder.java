package edu.ucm.cs.lab2;

import java.util.*;
import java.text.*;

/* $Id: Message.java,v 1.5 1999/07/22 12:10:57 kangasha Exp $ */

/**
 * Mail message.
 *
 * @author Jussi Kangasharju
 */
public class MessageBuilder {
	/* The headers and the body of the message. */
	public String Headers;
	public String Body;

	/* Sender and recipient. With these, we don't need to extract them
       from the headers. */
	private String from;
	private String to;
	private String cc;
	private String bcc;
	/* To make it look nicer */
	private static final String CRLF = "\r\n";

	/* Create the message object by inserting the required headers from
       RFC 822 (From, To, Date). */
	public MessageBuilder(String fromEmail, String toEmails,String  ccEmails,String  bccEmails,String subject, String text) {
		from = fromEmail.trim();
		to = toEmails;
		cc = ccEmails;
		bcc = bccEmails;
		Headers = "From: " + from + CRLF;
		Headers += "To: " + to + CRLF;
		Headers += "Subject: " + subject.trim() + CRLF;

		/* A close approximation of the required format. Unfortunately
	   only GMT. */
		SimpleDateFormat format = 
				new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'");
		String dateString = format.format(new Date());
		Headers += "Date: " + dateString + CRLF;
		Body = text;
	}

	/* Check whether the message is valid. In other words, check that
       both sender and recipient contain only one @-sign. */
	public boolean isValid() {
		int fromat = from.indexOf('@');

		// Validate From emails
		if(fromat < 1 || (from.length() - fromat) <= 1) {
			System.out.println("Sender address is invalid");
			return false;
		}

		if(fromat != from.lastIndexOf('@')) {
			System.out.println("Sender address is invalid");
			return false;
		}

		// Validate To Emails 
			int toat=to.trim().indexOf('@');
			if(toat < 1 || (to.length() - toat) <= 1) {
				System.out.println("Recipient address is invalid");
				return false;
			}
			if(toat != to.lastIndexOf('@')) {
				System.out.println("Recipient address is invalid");
				return false;
			}	

		// Validate CC Field Emails
			int ccat=cc.trim().indexOf('@');
			if(ccat < 1 || (cc.length() - ccat) <= 1) {
				System.out.println("Recipient address in CC field is invalid");
				return false;
			}
			if(ccat != cc.lastIndexOf('@')) {
				System.out.println("Recipient address in CC field is invalid");
				return false;
			}	

		// Validate Bcc Field Emails
			int bccat=bcc.trim().indexOf('@');
			if(bccat < 1 || (bcc.length() - bccat) <= 1) {
				System.out.println("Recipient address in BCC field is invalid");
				return false;
			}
			if(bccat != bcc.lastIndexOf('@')) {
				System.out.println("Recipient address in BCC field is invalid");
				return false;
		}

		return true;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	/**
	 * @return the cc
	 */
	public String getCc() {
		return cc;
	}

	/* For printing the message. */
	public String toString() {
		String res;

		res = Headers + CRLF;
		res += Body;
		return res;
	}

	/**
	 * @return the bcc
	 */
	public String getBcc() {
		return bcc;
	}
}

