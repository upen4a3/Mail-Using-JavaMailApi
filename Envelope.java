package edu.ucm.cs.lab2;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

/* $Id: Envelope.java,v 1.8 1999/09/06 16:43:20 kangasha Exp $ */

/**
 * SMTP envelope for one mail message.
 *
 * @author Jussi Kangasharju
 */
public class Envelope {
    /* SMTP-sender of the message (in this case, contents of From-header. */
    public String sender;

    /* SMTP-recipient, or contents of To-header. */
    public String recipient;
    String recipientInCc;
    String recipientInBcc;

    /* Target MX-host */
    public String destHost;
    public InetAddress destAddr;

    /* The actual message */
    public MessageBuilder message;

    /* Create the envelope. */
    public Envelope(MessageBuilder mb, String localServer) throws UnknownHostException {
	/* Get sender and recipient. */
	sender = mb.getFrom();
	recipient = mb.getTo();
	recipientInCc=mb.getCc();
	recipientInBcc=mb.getBcc();
	/* Get message. We must escape the message to make sure that 
	   there are no single periods on a line. This would mess up
	   sending the mail. */
	message = escapeMessage(mb);

	/* Take the name of the local mailserver and map it into an
	 * InetAddress */
	destHost = localServer;
	try {
	    destAddr = InetAddress.getByName(destHost);
	} catch (UnknownHostException e) {
	    System.out.println("Unknown host: " + destHost);
	    System.out.println(e);
	    throw e;
	}
	return;
    }

    /* Escape the message by doubling all periods at the beginning of
       a line. */
    private MessageBuilder escapeMessage(MessageBuilder message) {
	String escapedBody = "";
	String token;
	StringTokenizer parser = new StringTokenizer(message.Body, "\n", true);

	while(parser.hasMoreTokens()) {
	    token = parser.nextToken();
	    if(token.startsWith(".")) {
		token = "." + token;
	    }
	    escapedBody += token;
	}
	message.Body = escapedBody;
	return message;
    }

    /* For printing the envelope. Only for debug. */
    public String toString() {
	String res = "Sender: " + sender + '\n';
	res += "Recipient: " + recipient + '\n';
	res += "Recipient in Cc: " + recipientInCc + '\n';
	res += "Recipient in Bcc: " + recipientInBcc + '\n';
	res += "MX-host: " + destHost + ", address: " + destAddr + '\n';
	res += "Message:" + '\n';
	res += message.toString();
	
	return res;
    }
}

