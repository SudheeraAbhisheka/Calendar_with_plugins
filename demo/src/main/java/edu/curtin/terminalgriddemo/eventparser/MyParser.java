/* Generated By:JavaCC: Do not edit this line. MyParser.java */
package edu.curtin.terminalgriddemo.eventparser;


import java.util.*;

public class MyParser implements MyParserConstants {
    public static void main(String[] args) {

        MyParser parser = new MyParser(System.in);
          try {
                parser.EventList();
            } catch (ParseException e) {
                System.err.println("Parsing error: " + e.getMessage());
            }
    }

  final public Map<String, Object> EventList() throws ParseException {
 Map<String, Object> outputMap = new HashMap<String, Object>();

 Map<String, String> event = new HashMap<String, String>();
 ArrayList<Map<String, String>> events = new ArrayList<Map<String, String>>();

  Map<String, ArrayList<Map<String, String>>> plugin = new HashMap<String, ArrayList<Map<String, String>>>();
 ArrayList<Map<String, ArrayList<Map<String, String>>>> plugins = new ArrayList<Map<String, ArrayList<Map<String, String>>>>();
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case EVENT:
      case PLUGIN:
      case SCRIPT:
      case SPACES:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case EVENT:
        event = Event();
                   events.add(event);
        break;
      case PLUGIN:
        plugin = Plugin();
                                                           plugins.add(plugin);
        break;
      case SCRIPT:
        Script();
        break;
      case SPACES:
        jj_consume_token(SPACES);
        break;
      default:
        jj_la1[1] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
    outputMap.put("events", events);
    outputMap.put("plugins", plugins);

    {if (true) return outputMap;}
    throw new Error("Missing return statement in function");
  }

  final private Map<String, String> Event() throws ParseException {
    Map<String, String> eventMap = new HashMap<String, String>();
   String date = null;
   String time = null;
   String duration = null;
   String title = null;
    jj_consume_token(EVENT);
    jj_consume_token(SPACES);
    jj_consume_token(DATE);
                             date = token.image;
    jj_consume_token(SPACES);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case TIME:
      jj_consume_token(TIME);
                                                                      time = token.image;
      jj_consume_token(SPACES);
      jj_consume_token(NUMBER);
                                                                                                               duration = token.image;
      jj_consume_token(SPACES);
      jj_consume_token(STRING_LITERAL);
                                                                                                                                                                    title = token.image;
                 eventMap.put("date", date);
                 eventMap.put("time", time);
                 eventMap.put("duration", duration);
                 eventMap.put("title", title);

                 {if (true) return eventMap;}
      break;
    case ALL_DAY:
      jj_consume_token(ALL_DAY);
      jj_consume_token(SPACES);
      jj_consume_token(STRING_LITERAL);
                                         title = token.image;
            eventMap.put("date", date);
                 eventMap.put("title", title);

                 {if (true) return eventMap;}
      break;
    default:
      jj_la1[2] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final private Map<String, ArrayList<Map<String, String>>> Plugin() throws ParseException {
  String pluginName = null;
  Map<String, ArrayList<Map<String, String>>> plugin = new HashMap<String, ArrayList<Map<String, String>>>();
  ArrayList<Map<String, String>> properties = new ArrayList<Map<String, String>>();
  Map<String, String> property = new HashMap<String, String>();
    jj_consume_token(PLUGIN);
    jj_consume_token(SPACES);
    jj_consume_token(TEXT);
                              pluginName = token.image;
    jj_consume_token(SPACES);
    jj_consume_token(LBRACE);
    jj_consume_token(SPACES);
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case TEXT:
        ;
        break;
      default:
        jj_la1[3] = jj_gen;
        break label_2;
      }
      property = Property();
                                                                                                             properties.add(property);
    }
    jj_consume_token(RBRACE);
    plugin.put(pluginName, properties);

    {if (true) return plugin;}
    throw new Error("Missing return statement in function");
  }

  final private Map<String, String> Property() throws ParseException {
  String key = null;
  String value = null;
    jj_consume_token(TEXT);
         key = token.image;
    jj_consume_token(COLON);
    jj_consume_token(SPACES);
    jj_consume_token(STRING_LITERAL);
                                                                  value = token.image;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case COMMA:
      jj_consume_token(COMMA);
      break;
    default:
      jj_la1[4] = jj_gen;
      ;
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case SPACES:
      jj_consume_token(SPACES);
      break;
    default:
      jj_la1[5] = jj_gen;
      ;
    }
    Map<String, String> property = new HashMap<String, String>();
    property.put(key, value);

    {if (true) return property;}
    throw new Error("Missing return statement in function");
  }

  final public void Script() throws ParseException {
    jj_consume_token(SCRIPT);
    jj_consume_token(SPACES);
    jj_consume_token(STRING_LITERAL);
  }

  /** Generated Token Manager. */
  public MyParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[6];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x4034,0x4034,0x48,0x200,0x2000,0x4000,};
   }

  /** Constructor with InputStream. */
  public MyParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public MyParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new MyParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public MyParser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new MyParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public MyParser(MyParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(MyParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[15];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 6; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 15; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
