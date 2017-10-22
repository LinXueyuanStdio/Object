/*Copyright ©2016 TommyLemon(https://github.com/TommyLemon/APIJSON)

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

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**base model for reduce model codes
 * @author Lemon
 * @use extends BaseModel
 */
@SuppressWarnings("serial")
public abstract class BaseModel implements Serializable {

	private Long id;
	private Long date;

	public Long getId() {
		return id;
	}
	public BaseModel setId(Long id) {
		this.id = id;
		return this;
	}
	public Long getDate() {
		return date;
	}
	public BaseModel setDate(Long date) {
		this.date = date;
		return this;
	}

	//判断是否为空 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	/**判断collection是否为空
	 * @param collection
	 * @return
	 */
	public static <T> boolean isEmpty(Collection<T> collection) {
		return collection == null || collection.isEmpty();
	}
	/**判断map是否为空
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @return
	 */
	public static <K, V> boolean isEmpty(Map<K, V> map) {
		return map == null || map.isEmpty();
	}
	//判断是否为空 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>




	//判断是否包含 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	/**判断collection是否包含object
	 * @param collection
	 * @param object
	 * @return
	 */
	public static <T> boolean isContain(Collection<T> collection, T object) {
		return collection != null && collection.contains(object);
	}
	/**判断map是否包含key
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @param key
	 * @return
	 */
	public static <K, V> boolean isContainKey(Map<K, V> map, K key) {
		return map != null && map.containsKey(key);
	}
	/**判断map是否包含value
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @param value
	 * @return
	 */
	public static <K, V> boolean isContainValue(Map<K, V> map, V value) {
		return map != null && map.containsValue(value);
	}
	//判断是否为包含 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>



	
	//获取集合长度 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	/**获取数量
	 * @param <T>
	 * @param array
	 * @return
	 */
	public static <T> int count(T[] array) {
		return array == null ? 0 : array.length;
	}
	/**获取数量
	 * @param <T>
	 * @param collection List, Vector, Set等都是Collection的子类
	 * @return
	 */
	public static <T> int count(Collection<T> collection) {
		return collection == null ? 0 : collection.size();
	}
	/**获取数量
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @return
	 */
	public static <K, V> int count(Map<K, V> map) {
		return map == null ? 0 : map.size();
	}
	//获取集合长度 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>



	
	//获取集合长度 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	/**获取
	 * @param <T>
	 * @param array
	 * @return
	 */
	public static <T> T get(T[] array, int position) {
		return position < 0 || position >= count(array) ? null : array[position];
	}
	/**获取
	 * @param <T>
	 * @param collection List, Vector, Set等都是Collection的子类
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(Collection<T> collection, int position) {
		return (T) (collection == null ? null : get(collection.toArray(), position));
	}
	/**获取
	 * @param <K>
	 * @param <V>
	 * @param map null ? null
	 * @param key null ? null : map.get(key);
	 * @return
	 */
	public static <K, V> V get(Map<K, V> map, K key) {
		return key == null || map == null ? null : map.get(key);
	}
	//获取集合长度 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	


	
	//获取非基本类型对应基本类型的非空值 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	/**获取非空值
	 * @param value
	 * @return
	 */
	public static boolean value(Boolean value) {
		return value == null ? false : value;
	}
	/**获取非空值
	 * @param value
	 * @return
	 */
	public static int value(Integer value) {
		return value == null ? 0 : value;
	}
	/**获取非空值
	 * @param value
	 * @return
	 */
	public static long value(Long value) {
		return value == null ? 0 : value;
	}
	/**获取非空值
	 * @param value
	 * @return
	 */
	public static float value(Float value) {
		return value == null ? 0 : value;
	}
	/**获取非空值
	 * @param value
	 * @return
	 */
	public static double value(Double value) {
		return value == null ? 0 : value;
	}
	//获取非基本类型对应基本类型的非空值 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
