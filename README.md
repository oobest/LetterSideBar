# LetterSideBar

###第一步布局
'''
 <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager" />

    <com.oobest.lettersidebar.widget.LetterSideBar
        android:id="@+id/letter_side_bar"
        android:layout_width="32dp"
        android:layout_height="match_parent"
        android:layout_alignParentRight="true"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toStartOf="@+id/recyclerView" />

    <androidx.appcompat.widget.AppCompatTextView
        android:id="@+id/choose_letter_text"
        android:layout_width="56dp"
        android:layout_height="56dp"
        android:layout_centerInParent="true"
        android:layout_gravity="center"
        android:layout_marginBottom="731dp"
        android:background="#ffffff"
        android:elevation="4dp"
        android:gravity="center"
        android:text="A"
        android:textColor="#3f51b5"
        android:textSize="24sp"
        android:textStyle="bold"
        android:visibility="gone" />
'''

###第二部绑定
'''
  recyclerView.bindLetterSideBar(
            letterSideBar = letter_side_bar,
            fetchLetterList = { list },
            touchLetterCallback = {
                choose_letter_text.visibility = if (it.isNullOrEmpty()) View.GONE else View.VISIBLE
                choose_letter_text.text = it ?: ""
            })
'''
