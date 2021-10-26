package com.emily.emilyservice.utils.redis;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public interface Redis {

	Object get(String key);

	void set(String key, Object value);

	void setString(String key, String value);

	void expire(String key, int timeoutSecond);

	void expireString(String key, int timeoutSecond);

	void set(String key, Object value, int second);

	void setString(String key, String value, int second);

	String getString(String key);

	void hdel(String key, String field);

	void hdelString(String key, String field);

	void hset(String key, String field, Object value);

	void hsetString(String key, String field, String value);

	Object hget(String key, String field);

	String hgetString(String key, String field);

	void remove(String key);

	void removeString(String key);

	long getAutoId(String key);

	long getNum(String key);

	void clearAutoId(String key);

	void lpush(String key, Object value);

	void lpushString(String key, String value);

	void rpush(String key, Object value);

	void rpushString(String key, String value);

	List<Object> lrange(String key);

	List<String> lrangeString(String key);

	Object lpop(String key);

	String lpopString(String key);

	Object rpop(String key);

	String rpopString(String key);

	Object lindex(String key, int index);

	String lindexString(String key, int index);

	List<String> keys(String pattern);

	List<String> keysString(String pattern);

	List<String> keys();

	List<String> keysString();

	long llen(String key);

	long llenString(String key);

	boolean tryLock(String key);

	boolean tryLock(String key, int timeoutSecond);

	void releaseLock(String key);

	/**
	 * 如果 key 已经存在，并且值为字符串，那么这个命令会把 value 追加到原来值（value）的结尾。 如果 key
	 * 不存在，那么它将首先创建一个空字符串的key，再执行追加操作，这种情况 APPEND 将类似于 SET 操作
	 * 
	 * @param key
	 * @param value
	 */
	void append(String key, String value);

	void appendString(String key, String value);

	boolean exist(String key);

	boolean existString(String key);

	/**
	 * 返回 key 指定的哈希集中所有字段的名字
	 * 
	 * @param key
	 * @return
	 */
	Set<String> hkeys(String key);

	Set<String> hkeysString(String key);

	/**
	 * 从存于 key 的列表里移除前 count 次出现的值为 value 的元素。 这个 count 参数通过下面几种方式影响这个操作： count
	 * > 0: 从头往尾移除值为 value 的元素。 count < 0: 从尾往头移除值为 value 的元素。 count = 0: 移除所有值为
	 * value 的元素。
	 * 
	 * @param key
	 * @param count
	 * @param value
	 */
	void lrem(String key, long count, String value);

	void lremString(String key, long count, String value);

	/**
	 * 向set集合中插入String值
	 * @param setName
	 * @param setValues
	 */
	void saddString(String setName,String[] setValues);

	/**
	 * 向set集合中插入Object值
	 * @param setName
	 * @param setValues
	 * @return 插入成功返回1，失败返回0
	 */
	void saddObject(String setName,Object[] setValues);

	/**
	 * 从集合中删除String值
	 * @param setName
	 * @param setValues
	 */
	void sremString(String setName,String[] setValues);

	/**
	 * 从集合中删除String值
	 * @param setName
	 * @param setValues
	 */
	void sremObject(String setName,Object[] setValues);

	/**
	 * 随机删除并返回集合set中的String元素
	 * @param setName
	 * @return
	 */
	String spopString(String setName);

	/**
	 * 返回多个集合的差集
	 * @param setNames
	 * @return
	 */
	Set<String> sdiff(String[] setNames);

	/**
	 * 将多个集合的差集存在另一个目标集合中
	 * @param setNames 多个集合数组
	 * @param dstsetName 目标集合
	 */
	void sdiffstore(String[] setNames,String dstsetName);

	/**
	 * 返回多个集合的交集
	 * @param setNames
	 * @return
	 */
	Set<String> sinter(String[] setNames);

	/**
	 * 将多个集合的交集存储到目标集合
	 * @param setNames
	 * @param dstsetName
	 */
	void sinterstore(String[] setNames,String dstsetName);

	/**
	 * 返回多个集合的并集
	 * @param setNames
	 * @return
	 */
	Set<String> sunion(String[] setNames);

	/**
	 * 将多个集合的并集存储到目标集合
	 * @param setNames
	 * @param dstsetName
	 */
	void sunionstore(String[] setNames,String dstsetName);

	/**
	 * 将源集合中的value移动到目标集合中
	 * @param srcsetNames
	 * @param dstsetName
	 * @param value
	 */
	void smove(String srcsetNames,String dstsetName,String value);

	/**
	 * 集合的元素个数
	 * @param setName
	 * @return
	 */
	Long scard(String setName);

	/**
	 * 判断value是否在集合中
	 * @param setName
	 * @param value
	 * @return
	 */
	Boolean sismember(String setName,String value);

	/**
	 * 随机返回集合中count个元素
	 * @param setName
	 * @param count
	 * @return
	 */
	List<String> srandmember(String setName,int count);

	/**
	 * 查看集合
	 * @param setName
	 * @return
	 */
	Set<String> smembers(String setName);


	/**
	 * 往有序集合中添加元素
	 * @param setName
	 * @param score
	 * @param value
	 */
	void zadd(String setName,double score,String value);

	/**
	 * 删除有序集合中的元素value
	 * @param setName
	 * @param value
	 */
	void zrem(String setName,String[] value);

	/**
	 * 给元素value对应的score增加increment
	 * @param setName
	 * @param increment
	 * @param value
	 */
	void zincrby(String setName,double increment ,String value);

	/**
	 * 返回元素的排名（按照score从小到大排名）
	 * @param setName
	 * @param value
	 * @return
	 */
	Long zrank(String setName,String value);

	/**
	 * 返回元素的排名（按照score从大到小排名）
	 * @param setName
	 * @param value
	 * @return
	 */
	Long zrevrank(String setName,String value);

	/**
	 * 返回score中从大到小的排列中第start-end区间的元素
	 * @param setName
	 * @param start
	 * @param end
	 * @return
	 */
	Set<String> zrevrange(String setName,int start ,int end);

	/**
	 * 返回score在[max,min]区间内的元素
	 * @param setName
	 * @param max
	 * @param min
	 * @return
	 */
	Set<String> zrangeByScore(String setName,int min,int max );

	/**
	 * 返回score在[max,min]区间内元素的个数
	 * @param setName
	 * @param min
	 * @param max
	 * @return
	 */
	Long zcount(String setName,int min,int max );

	/**
	 * 返回有序集合中元素的个数
	 * @param setName
	 * @return
	 */
	Long zcard(String setName);

	/**
	 * 返回对应元素的score
	 * @param setName
	 * @param value
	 * @return
	 */
	Double zscore(String setName,String value);

	/**
	 * 删除指定顺序区间[start,end]内的元素
	 * @param setName
	 * @param start
	 * @param end
	 */
	void zremrangeByRank(String setName,long start,long end);

	/**
	 * 删除指定score在[min,max]内的元素
	 * @param setName
	 * @param min
	 * @param max
	 */
	void zremrangeByScore(String setName,double min,double max);

	<T> Map<T, Object> batchGet(Collection<T> entities, Function<T, String> getKeyFunc);

	<T> Map<T, String> batchGetString(Collection<T> entities, Function<T, String> getKeyFunc);
	
	<T> void batchSet(Collection<T> entities, Function<T, String> getKeyFunc,int seconds);
}
