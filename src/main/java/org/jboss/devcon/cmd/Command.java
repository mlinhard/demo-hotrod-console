package org.jboss.devcon.cmd;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.infinispan.Cache;

public abstract class Command {

	private static Pattern CMD_LINE_DELIM = Pattern.compile("\\s");
	private static Pattern QUOT_STR1 = Pattern.compile("\\\".*");
	private static Pattern QUOT_STR2 = Pattern.compile("\\\".*?\\\"");
	private static final String EXIT = "exit";
	private static final String QUIT = "quit";
	private static final String PUT = "put";
	private static final String GET = "get";
	private static final String DEL = "del";

	public abstract String execute(Cache<String, String> cache);

	private static boolean hasNextQuotedString(Scanner sc) {
		return sc.hasNext(QUOT_STR1);
	}

	private static String nextQuotedString(Scanner sc) {
		if (!sc.hasNext())
			throw new java.util.NoSuchElementException();
		if (!hasNextQuotedString(sc))
			throw new java.util.InputMismatchException();
		String s = sc.findInLine(QUOT_STR2);
		if (s == null)
			throw new java.util.InputMismatchException();
		return s.substring(1, s.length() - 1);
	}

	public static Command parse(String cmdLine) {
		if (cmdLine == null || cmdLine.equals("")) {
			return null;
		}
		Scanner cmdScan = new Scanner(cmdLine);
		String cmd = cmdScan.next();
		if (cmd == null) {
			return null;
		}
		try {
			if (EXIT.equals(cmd) || QUIT.equals(cmd)) {
				return new ExitCommand();
			} else if (PUT.equals(cmd)) {
				String key = null;
				if (hasNextQuotedString(cmdScan)) {
					key = nextQuotedString(cmdScan);
				} else {
					key = cmdScan.next();
				}
				String value = null;
				if (hasNextQuotedString(cmdScan)) {
					value = nextQuotedString(cmdScan);
				} else {
					value = cmdScan.next();
				}
				return new PutCommand(key, value);
			} else if (GET.equals(cmd)) {
				String key = null;
				if (hasNextQuotedString(cmdScan)) {
					key = nextQuotedString(cmdScan);
				} else {
					key = cmdScan.next();
				}
				return new GetCommand(key);
			} else if (DEL.equals(cmd)) {
				String key = null;
				if (hasNextQuotedString(cmdScan)) {
					key = nextQuotedString(cmdScan);
				} else {
					key = cmdScan.next();
				}
				return new DeleteCommand(key);
			} else {
				return new UnknownCommand();
			}
		} catch (NoSuchElementException e) {
			System.out.println("invalid command format");
			return null;
		}
		// return null;
	}
}
