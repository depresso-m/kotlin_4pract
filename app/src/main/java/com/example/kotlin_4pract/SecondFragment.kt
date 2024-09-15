package com.example.kotlin_4pract

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_4pract.databinding.FragmentSecondBinding
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader


class SecondFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.to1fragmentBtn.setOnClickListener {
            findNavController().navigate(R.id.action_secondFragment_to_firstFragment)
        }

        val recyclerView: RecyclerView = binding.recyclerView
        val data = readFile()
        val adapter = MyAdapter(data)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun readFile(): List<String> {
        val dir = File(requireContext().filesDir, "photos")
        val file = File(dir, "date.txt")

        val dataList = mutableListOf<String>()

        try {
            if (!file.exists()) {
                throw FileNotFoundException("File does not exist: ${file.absolutePath}")
            }

            val inputStream = FileInputStream(file)
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))

            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                dataList.add(line!!)
            }

            bufferedReader.close()
            inputStream.close()
        } catch (e: FileNotFoundException) {
            println("Error: ${e.message}")
        } catch (e: IOException) {
            println("Error reading file: ${e.message}")
        }

        println("Read data: $dataList") // Добавьте это для отладки

        return dataList
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}