package com.jstarcraft.rns.search.converter.index;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.lucene.document.DoublePoint;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FloatPoint;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexableField;

import com.jstarcraft.core.common.reflection.TypeUtility;
import com.jstarcraft.rns.search.annotation.SearchIndex;
import com.jstarcraft.rns.search.converter.IndexConverter;
import com.jstarcraft.rns.search.converter.SearchContext;
import com.jstarcraft.rns.search.exception.SearchException;

/**
 * 数组索引转换器
 * 
 * @author Birdy
 *
 */
public class ArrayIndexConverter implements IndexConverter {

    private int[] wrappers2Primitives(byte[] oldArray) {
        int size = oldArray.length;
        int[] newArray = new int[size];
        for (int index = 0; index < size; index++) {
            newArray[index] = oldArray[index];
        }
        return newArray;
    }

    private int[] wrappers2Primitives(Byte[] oldArray) {
        int size = oldArray.length;
        int[] newArray = new int[size];
        for (int index = 0; index < size; index++) {
            newArray[index] = oldArray[index].intValue();
        }
        return newArray;
    }

    private int[] wrappers2Primitives(short[] oldArray) {
        int size = oldArray.length;
        int[] newArray = new int[size];
        for (int index = 0; index < size; index++) {
            newArray[index] = oldArray[index];
        }
        return newArray;
    }

    private int[] wrappers2Primitives(Short[] oldArray) {
        int size = oldArray.length;
        int[] newArray = new int[size];
        for (int index = 0; index < size; index++) {
            newArray[index] = oldArray[index].intValue();
        }
        return newArray;
    }

    private int[] wrappers2Primitives(Integer[] oldArray) {
        int size = oldArray.length;
        int[] newArray = new int[size];
        for (int index = 0; index < size; index++) {
            newArray[index] = oldArray[index].intValue();
        }
        return newArray;
    }

    private long[] wrappers2Primitives(Long[] oldArray) {
        int size = oldArray.length;
        long[] newArray = new long[size];
        for (int index = 0; index < size; index++) {
            newArray[index] = oldArray[index].longValue();
        }
        return newArray;
    }

    private float[] wrappers2Primitives(Float[] oldArray) {
        int size = oldArray.length;
        float[] newArray = new float[size];
        for (int index = 0; index < size; index++) {
            newArray[index] = oldArray[index].floatValue();
        }
        return newArray;
    }

    private double[] wrappers2Primitives(Double[] oldArray) {
        int size = oldArray.length;
        double[] newArray = new double[size];
        for (int index = 0; index < size; index++) {
            newArray[index] = oldArray[index].doubleValue();
        }
        return newArray;
    }

    @Override
    public Iterable<IndexableField> convert(SearchContext context, String path, Field field, SearchIndex annotation, Type type, Object data) {
        Collection<IndexableField> indexables = new LinkedList<>();
        Class<?> componentClass = null;
        Type componentType = null;
        if (type instanceof GenericArrayType) {
            GenericArrayType genericArrayType = GenericArrayType.class.cast(type);
            componentType = genericArrayType.getGenericComponentType();
            componentClass = TypeUtility.getRawType(componentType, null);
        } else {
            Class<?> clazz = TypeUtility.getRawType(type, null);
            componentType = clazz.getComponentType();
            componentClass = clazz.getComponentType();

            if (Byte[].class == clazz) {
                int[] array = wrappers2Primitives((Byte[]) data);
                indexables.add(new IntPoint(path, array));
                return indexables;
            }
            if (byte[].class == clazz) {
                int[] array = wrappers2Primitives((byte[]) data);
                indexables.add(new IntPoint(path, array));
                return indexables;
            }
            if (Short[].class == clazz) {
                int[] array = wrappers2Primitives((Short[]) data);
                indexables.add(new IntPoint(path, array));
                return indexables;
            }
            if (short[].class == clazz) {
                int[] array = wrappers2Primitives((short[]) data);
                indexables.add(new IntPoint(path, array));
                return indexables;
            }
            if (Integer[].class == clazz) {
                int[] array = wrappers2Primitives((Integer[]) data);
                indexables.add(new IntPoint(path, array));
                return indexables;
            }
            if (int[].class == clazz) {
                indexables.add(new IntPoint(path, (int[]) data));
                return indexables;
            }
            if (Long[].class == clazz) {
                long[] array = wrappers2Primitives((Long[]) data);
                indexables.add(new LongPoint(path, array));
            }
            if (long[].class == clazz) {
                indexables.add(new LongPoint(path, (long[]) data));
                return indexables;
            }
            if (Float[].class == clazz) {
                float[] array = wrappers2Primitives((Float[]) data);
                indexables.add(new FloatPoint(path, array));
            }
            if (float[].class == clazz) {
                indexables.add(new FloatPoint(path, (float[]) data));
                return indexables;
            }
            if (Double[].class == clazz) {
                double[] array = wrappers2Primitives((Double[]) data);
                indexables.add(new DoublePoint(path, array));
            }
            if (double[].class == clazz) {
                indexables.add(new DoublePoint(path, (double[]) data));
                return indexables;
            }
            if (String[].class == clazz) {
                for (String string : (String[]) data) {
                    indexables.add(new StringField(path, string, Store.NO));
                }
                return indexables;
            }
        }
        throw new SearchException();
    }

}
