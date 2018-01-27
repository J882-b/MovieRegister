package foo;


import android.support.v7.util.DiffUtil;
import android.support.v7.util.ListUpdateCallback;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;


@RunWith(RobolectricTestRunner.class)
public class DiffUtilTest {

    @Test
    public void diffUtil() {

        List<Foo> oldList = new ArrayList<>();
        oldList.add(new Foo(1));
        oldList.add(new Foo(2));
        oldList.add(new Foo(3));
        oldList.add(new Foo(4));

        List<Foo> newList = new ArrayList<>();
        newList.add(new Foo(2));
        newList.add(new Foo(3));
        newList.add(new Foo(4));
        newList.add(new Foo(5));

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return oldList.size();
            }

            @Override
            public int getNewListSize() {
                return newList.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return oldList.get(oldItemPosition).getId()
                        .equals(newList.get(newItemPosition).getId());
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return oldList.get(oldItemPosition)
                        .equals(newList.get(newItemPosition));
            }
        }, false);

        diffResult.dispatchUpdatesTo(new ListUpdateCallback() {
            @Override
            public void onInserted(int position, int count) {
                System.out.println("onInserted() position: " + position + " count: " + count);
            }

            @Override
            public void onRemoved(int position, int count) {
                System.out.println("onRemoved() position: " + position + " count: " + count);;
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                System.out.println("onMoved() fromPosition: " + fromPosition + " toPosition: " + toPosition);
            }

            @Override
            public void onChanged(int position, int count, Object payload) {
                System.out.println("onChanged() position: " + position + " count: " + count + " payload: " + payload);
            }
        });

        assertEquals(Arrays.asList(new Foo(2), new Foo(3), new Foo(4), new Foo(5)), oldList);
    }

    public static class Foo {
        private final String id;
        private final String value;

        public Foo(int id) {
            this.id = String.valueOf(id);
            this.value = String.valueOf(id);
        }

        public String getId() {
            return id;
        }

        public String getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Foo foo = (Foo) o;

            if (id != null ? !id.equals(foo.id) : foo.id != null) return false;
            return value != null ? value.equals(foo.value) : foo.value == null;
        }

        @Override
        public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + (value != null ? value.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Foo{" +
                    "id='" + id + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }
}
