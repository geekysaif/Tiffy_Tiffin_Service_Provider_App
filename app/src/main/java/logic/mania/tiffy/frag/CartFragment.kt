package logic.mania.tiffy.frag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import logic.mania.tiffy.R

/**
 * A simple [Fragment] subclass.
 */
class CartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment



        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.findViewById<TextView>(R.id.btn_order_now).setOnClickListener {
            val fragment: Fragment = HomeFragment()
            loadFragment(fragment)
        }
    }


    /*common method for load Fragment*/
    private fun loadFragment(fragment: Fragment) {

        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.setCustomAnimations(
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        )
        transaction?.replace(R.id.container, fragment)
        transaction?.commit()

    }
}
