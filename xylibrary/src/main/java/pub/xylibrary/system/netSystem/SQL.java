/*Copyright ©2016 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package pub.xylibrary.system.netSystem;

/**SQL语句，函数名尽量和JDK中相同或类似功能的函数的名称一致
 * @author Lemon
 */
public class SQL {

	public static final String OR = " OR ";
	public static final String AND = " AND ";
	public static final String NOT = " NOT ";
	public static final String IS = " is ";
	public static final String NULL = " null ";

	/**
	 * isNull = true 
	 * @return {@link #isNull(boolean)}
	 */
	public static String isNull() {
		return isNull(true);
	}
	/**
	 * @param isNull
	 * @return IS + (isNull ? "" : NOT) + NULL;
	 */
	public static String isNull(boolean isNull) {
		return IS + (isNull ? "" : NOT) + NULL;
	}
	/**
	 * isNull = true
	 * @param s
	 * @return {@link #isNull(String, boolean)}
	 */
	public static String isNull(String s) {
		return isNull(s, true);
	}
	/**
	 * @param s
	 * @param isNull
	 * @return s + {@link #isNull(boolean)}
	 */
	public static String isNull(String s, boolean isNull) {
		return s + isNull(isNull);
	}

	/**
	 * isEmpty = true
	 * @param s
	 * @return {@link #isEmpty(String, boolean)}
	 */
	public static String isEmpty(String s) {
		return isEmpty(s, true);
	}
	/**
	 * trim = false
	 * @param s
	 * @param isEmpty
	 * @return {@link #isEmpty(String, boolean, boolean)}
	 */
	public static String isEmpty(String s, boolean isEmpty) {
		return isEmpty(s, isEmpty, false);
	}
	/**
	 * nullable = true
	 * @param s
	 * @param isEmpty <=0
	 * @param trim s = trim(s);
	 * @return {@link #isEmpty(String, boolean, boolean, boolean)}
	 */
	public static String isEmpty(String s, boolean isEmpty, boolean trim) {
		return isEmpty(s, isEmpty, trim, true);
	}
	/**
	 * @param s
	 * @param isEmpty <=0
	 * @param trim s = trim(s);
	 * @param nullable isNull(s, true) + OR +
	 * @return {@link #lengthCompare(String, String)}
	 */
	public static String isEmpty(String s, boolean isEmpty, boolean trim, boolean nullable) {
		if (trim) {
			s = trim(s);
		}
		return (nullable ? isNull(s, true) + OR : "") + lengthCompare(s, (isEmpty ? "<=" : ">") + "0");
	}
	/**
	 * @param s 因为POWER(x,y)等函数含有不只一个key，所以需要客户端添加进去，服务端检测到条件中有'('和')'时就不转换，直接当SQL语句查询
	 * @return {@link #length(String)} + compare
	 */
	public static String lengthCompare(String s, String compare) {
		return length(s) + compare;
	}


	/**
	 * @param s 因为POWER(x,y)等函数含有不只一个key，所以需要客户端添加进去，服务端检测到条件中有'('和')'时就不转换，直接当SQL语句查询
	 * @return "length(" + s + ")"
	 */
	public static String length(String s) {
		return "length(" + s + ")";
	}
	/**
	 * @param s 因为POWER(x,y)等函数含有不只一个key，所以需要客户端添加进去，服务端检测到条件中有'('和')'时就不转换，直接当SQL语句查询
	 * @return "char_length(" + s + ")"
	 */
	public static String charLength(String s) {
		return "char_length(" + s + ")";
	}

	/**
	 * @param s
	 * @return "trim(" + s + ")"
	 */
	public static String trim(String s) {
		return "trim(" + s + ")";
	}
	/**
	 * @param s
	 * @return "ltrim(" + s + ")"
	 */
	public static String trimLeft(String s) {
		return "ltrim(" + s + ")";
	}
	/**
	 * @param s
	 * @return "rtrim(" + s + ")"
	 */
	public static String trimRight(String s) {
		return "rtrim(" + s + ")";
	}

	/**
	 * @param s
	 * @param n
	 * @return "left(" + s + "," + n + ")"
	 */
	public static String left(String s, int n) {
		return "left(" + s + "," + n + ")";
	}
	/**
	 * @param s
	 * @param n
	 * @return "right(" + s + "," + n + ")"
	 */
	public static String right(String s, int n) {
		return "right(" + s + "," + n + ")";
	}

	/**
	 * @param s
	 * @param start
	 * @param end
	 * @return "substring(" + s + "," + start + "," + (end-start) + ")"
	 */
	public static String subString(String s, int start, int end) {
		return "substring(" + s + "," + start + "," + (end-start) + ")";
	}

	/**
	 * @param s
	 * @param c
	 * @return "instr(" + s + "," + c + ")"
	 */
	public static String indexOf(String s, String c) {
		return "instr(" + s + "," + c + ")";
	}

	/**
	 * @param s
	 * @param c1
	 * @param c2
	 * @return
	 */
	public static String replace(String s, String c1, String c2) {
		return "replace(" + s + "," + c1 + "," + c2 + ")";
	}

	/**
	 * @param s1
	 * @param s2
	 * @return "strcmp(" + s1 + "," + s2 + ")"
	 */
	public static String equals(String s1, String s2) {
		return "strcmp(" + s1 + "," + s2 + ")";
	}

	/**
	 * @param s
	 * @return "upper(" + s + ")"
	 */
	public static String toUpperCase(String s) {
		return "upper(" + s + ")";
	}
	/**
	 * @param s
	 * @return "lower(" + s + ")"
	 */
	public static String toLowerCase(String s) {
		return "lower(" + s + ")";
	}





	public static final int SEARCH_TYPE_CONTAIN_FULL = 0;
	public static final int SEARCH_TYPE_CONTAIN_ORDER = 1;
	public static final int SEARCH_TYPE_CONTAIN_SINGLE = 2;
	public static final int SEARCH_TYPE_CONTAIN_ANY = 3;
	public static final int SEARCH_TYPE_START = 4;
	public static final int SEARCH_TYPE_END = 5;
	public static final int SEARCH_TYPE_START_SINGLE = 6;
	public static final int SEARCH_TYPE_END_SINGLE = 7;
	public static final int SEARCH_TYPE_PART_MATCH = 8;
	/**获取搜索值
	 * @param s
	 * @return
	 */
	public static String search(String s) {
		return search(s, SEARCH_TYPE_CONTAIN_FULL);
	}
	/**获取搜索值
	 * @param s
	 * @param type
	 * @return
	 */
	public static String search(String s, int type) {
		return search(s, type, true);
	}
	/**获取搜索值
	 * @param s
	 * @param type
	 * @param ignoreCase
	 * @return default SEARCH_TYPE_CONTAIN_FULL
	 */
	public static String search(String s, int type, boolean ignoreCase) {
		if (s == null) {
			return null;
		}
		switch (type) {
		case SEARCH_TYPE_CONTAIN_SINGLE:
			return "_" + s + "_";
		case SEARCH_TYPE_CONTAIN_ORDER:
			char[] cs = s.toCharArray();
			if (cs == null) {
				return null;
			}
			String value = "%";
			for (int i = 0; i < cs.length; i++) {
				value += cs[i] + "%";
			}
			return value;
		case SEARCH_TYPE_START:
			return s + "%";
		case SEARCH_TYPE_END:
			return "%" + s;
		case SEARCH_TYPE_START_SINGLE:
			return s + "_";
		case SEARCH_TYPE_END_SINGLE:
			return "_" + s;
		case SEARCH_TYPE_CONTAIN_ANY:
		case SEARCH_TYPE_PART_MATCH:
			cs = s.toCharArray();
			if (cs == null) {
				return null;
			}
			value = "";
			for (int i = 0; i < cs.length; i++) {
				value += search("" + cs[i], SEARCH_TYPE_CONTAIN_FULL, ignoreCase);
			}
			return value;
		default://SEARCH_TYPE_CONTAIN_FULL
			return "%" + s + "%";
		}
	}

}
