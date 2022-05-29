package com.example.brogrow.utill

import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.brogrow.model.Address
import org.json.JSONException
import org.json.JSONObject
import java.text.Normalizer
import java.util.*
import java.util.regex.Pattern

fun makeSlug(input: String?): String? {
    var NONLATIN = Pattern.compile("[^\\w_-]");
    var SEPARATORS = Pattern.compile("[\\s\\p{Punct}&&[^-]]");
    val noseparators: String = SEPARATORS.matcher(input).replaceAll("-")
    val normalized: String = Normalizer.normalize(noseparators, Normalizer.Form.NFD)
    val slug: String = NONLATIN.matcher(normalized).replaceAll("")
    return slug.lowercase(Locale.ENGLISH).replace("-{2,}".toRegex(), "-")
        .replace("^-|-$".toRegex(), "")
}

fun getDataFromPinCode(pinCode: String, context: Context): Address? {

    var address: Address? = null

    val url = "http://www.postalpincode.in/api/pincode/$pinCode"

    val queue = Volley.newRequestQueue(context)

    val objectRequest =
        JsonObjectRequest(Request.Method.GET, url, null, object : Response.Listener<JSONObject?> {

            override fun onResponse(response: JSONObject?) {

                try {

                    val postOfficeArray = response!!.getJSONArray("PostOffice")
                    if (response!!.getString("Status") == "Error") {

                    } else {

                        val obj = postOfficeArray.getJSONObject(0)

                        if (address != null) {
                            address.district = obj.getString("District").toString()
                        }
                        if (address != null) {
                            address.state = obj.getString("State").toString()
                        }

                        if (address != null) {
                            address.country = obj.getString("Country")
                        }

                    }
                } catch (e: JSONException) {

                    e.printStackTrace()
                }
            }
        }) { error ->
            Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT)
                .show()
        }

    queue.add(objectRequest)

    return address
}