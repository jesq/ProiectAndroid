import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import com.example.todoapp.R
import com.example.todoapp.User


class UserListAdapter(internal var activity : Activity,
                      internal var userList : List<User>,
                      internal var edt_id : Long,
                      internal var edt_userName:EditText,
                      internal var edt_email:EditText,
                      internal var etd_password:EditText):BaseAdapter() {

    internal var inflater:LayoutInflater

    init{
        inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView:View
        rowView = inflater.inflate(R.layout.activity_registration, null)
        return rowView
    }

    override fun getItem(position: Int): Any {
        return userList[position]
    }

    override fun getItemId(position: Int): Long {
        return userList[position].userID.toLong()
    }

    override fun getCount(): Int {
        return userList.size
    }

}