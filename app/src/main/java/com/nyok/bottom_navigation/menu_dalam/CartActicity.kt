package com.nyok.bottom_navigation.menu_dalam
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project1762.Helper.ChangeNumberItemsListener
import com.example.project1762.Helper.ManagmentCart
import com.nyok.bottom_navigation.R
import com.nyok.bottom_navigation.adapter.CartAdapter
import com.nyok.bottom_navigation.databinding.FragmentKeranjangBinding

class CartActicity : AppCompatActivity() {
    private lateinit var binding: FragmentKeranjangBinding
    private lateinit var managmentCart: ManagmentCart
    private var tax:Double=0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentKeranjangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart=ManagmentCart(this)

        setVariable()
        calcualtorCart()
        initCartList()

    }

    private fun initCartList() {
        binding.viewCart.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.viewCart.adapter=CartAdapter(managmentCart.getListCart(),this,object :ChangeNumberItemsListener{
            override fun onChanged() {
                calcualtorCart()
            }
        })
        with(binding){
            emptyTxt.visibility=if (managmentCart.getListCart().isEmpty())View.VISIBLE else View.GONE
            scroll.visibility=if (managmentCart.getListCart().isEmpty())View.GONE else View.VISIBLE
        }

    }

    private fun setVariable() {
        binding.apply {
            BackBtn.setOnClickListener {
                finish()
            }

            method1.setOnClickListener {
                method1.setBackgroundResource(R.drawable.custom_btn_blue)
                methodIc1.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this@CartActicity, R.color.white))
                method1Title1.setTextColor(getResources().getColor(R.color.white))
                methodsubtitle1.setTextColor(getResources().getColor(R.color.white))

                method2.setBackgroundResource(R.drawable.grey_background)
                methodIc2.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this@CartActicity, R.color.black))
                method2Title2.setTextColor(getResources().getColor(R.color.black))
                methodsubtitle2.setTextColor(getResources().getColor(R.color.black))
            }
            method2.setOnClickListener{
                method2.setBackgroundResource(R.drawable.custom_btn_blue)
                methodIc2.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this@CartActicity, R.color.white))
                method2Title2.setTextColor(getResources().getColor(R.color.white))
                methodsubtitle2.setTextColor(getResources().getColor(R.color.white))

                method1.setBackgroundResource(R.drawable.grey_background)
                methodIc1.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this@CartActicity, R.color.black))
                method1Title1.setTextColor(getResources().getColor(R.color.black))
                methodsubtitle1.setTextColor(getResources().getColor(R.color.black))
            }
        }
    }

    private fun calcualtorCart(){
        val percentTax=0.02
        val delivery=10.0
        tax=Math.round((managmentCart.getTotalFee()*percentTax)*100)/100.0
        val total =Math.round((managmentCart.getTotalFee()*tax+delivery)*100)/100
        val itemtotal=Math.round(managmentCart.getTotalFee()*100)/100

        with(binding){
            totalFeeTxt.text="$$itemtotal"
            TaxTxt.text="$$tax"
            deliveryTxt.text="$$delivery"
            totalTxt.text="$$total"
        }
    }
}


