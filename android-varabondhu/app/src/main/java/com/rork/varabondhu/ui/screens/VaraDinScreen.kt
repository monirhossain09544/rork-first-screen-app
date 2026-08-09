package com.rork.varabondhu.ui.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.vector.ImageVector
import com.rork.varabondhu.R
import com.rork.varabondhu.ui.theme.*
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VaraDinScreen(
    onNavigateBack: () -> Unit
) {
    var selectedVehicle by remember { mutableStateOf("রিকশা") }
    var fareAmount by remember { mutableStateOf("") }
    var noteText by remember { mutableStateOf("") }
    
    var showHowItWorksBottomSheet by remember { mutableStateOf(false) }

    // Formatting states
    var selectedDateStr by remember { mutableStateOf("২৮ মে ২০২৪") }
    var selectedTimeStr by remember { mutableStateOf("৫:৩০ PM") }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val cal = Calendar.getInstance()
            cal.set(year, month, dayOfMonth)
            val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            selectedDateStr = sdf.format(cal.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            val amPm = if (hourOfDay >= 12) "PM" else "AM"
            val hr = if (hourOfDay > 12) hourOfDay - 12 else if (hourOfDay == 0) 12 else hourOfDay
            val min = minute.toString().padStart(2, '0')
            selectedTimeStr = "$hr:$min $amPm"
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        false
    )

    val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0

    Scaffold(
        containerColor = PageWhite,
        topBar = {
            // Sticky Header
            Surface(
                color = PageWhite,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onNavigateBack, modifier = Modifier.size(40.dp)) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = Ink
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ভাড়া দিন",
                        fontFamily = BanglaFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Ink,
                        modifier = Modifier.weight(1f)
                    )
                    
                    // "কিভাবে কাজ করে?" Button
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = CardWhite,
                        border = BorderStroke(1.dp, FieldBorder),
                        modifier = Modifier.clickable { showHowItWorksBottomSheet = true }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.HelpOutline,
                                contentDescription = null,
                                tint = BrandGreen,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "কিভাবে কাজ করে?",
                                fontFamily = BanglaFamily,
                                fontSize = 12.sp,
                                color = InkMuted
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            // Sticky Bottom Button
            if (!isImeVisible) {
                Surface(
                    color = PageWhite,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
                    ) {
                        HorizontalDivider(color = FieldBorder.copy(alpha = 0.5f))
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { /* Submit logic */ },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                                .height(52.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = BrandGreen)
                        ) {
                            Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "ভাড়া জমা দিন",
                                fontFamily = BanglaFamily,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
                .verticalScroll(rememberScrollState())
        ) {
            // Location Card
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 0.dp, bottom = 8.dp),
                shape = RoundedCornerShape(16.dp),
                color = CardWhite,
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Timeline Graphics
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, end = 16.dp)
                    ) {
                        // Start Dot
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(BrandGreen.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(BrandGreen)
                            )
                        }
                        
                        // Dashed Line
                        Canvas(modifier = Modifier
                            .width(2.dp)
                            .height(40.dp)
                            .padding(vertical = 4.dp)) {
                            drawLine(
                                color = FieldBorder,
                                start = Offset(size.width / 2, 0f),
                                end = Offset(size.width / 2, size.height),
                                strokeWidth = 2.dp.toPx(),
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                            )
                        }
                        
                        // End Marker (Red Pin)
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = null,
                            tint = Color(0xFFE53935),
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    // Locations
                    Column(modifier = Modifier.weight(1f)) {
                        // Start Location
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "যাত্রার শুরু",
                                    fontFamily = BanglaFamily,
                                    fontSize = 12.sp,
                                    color = InkMuted
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "মিরপুর ১০, ঢাকা",
                                    fontFamily = BanglaFamily,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Ink
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable { /* Change start */ }
                            ) {
                                Text(
                                    text = "পরিবর্তন",
                                    fontFamily = BanglaFamily,
                                    fontSize = 13.sp,
                                    color = BrandGreen,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Outlined.Edit,
                                    contentDescription = null,
                                    tint = BrandGreen,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                        
                        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = FieldBorder.copy(alpha = 0.5f))
                        
                        // End Location
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "গন্তব্য স্থান",
                                    fontFamily = BanglaFamily,
                                    fontSize = 12.sp,
                                    color = InkMuted
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "ফার্মগেট, ঢাকা",
                                    fontFamily = BanglaFamily,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Ink
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable { /* Change end */ }
                            ) {
                                Text(
                                    text = "পরিবর্তন",
                                    fontFamily = BanglaFamily,
                                    fontSize = 13.sp,
                                    color = BrandGreen,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Outlined.Edit,
                                    contentDescription = null,
                                    tint = BrandGreen,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Vehicle Selection
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "যানবাহন নির্বাচন করুন",
                fontFamily = BanglaFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Ink,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            val vehicles = listOf(
                VehicleOption("রিকশা", R.drawable.ic_rickshaw),
                VehicleOption("অটো / CNG", R.drawable.ic_cng),
                VehicleOption("বাস", R.drawable.ic_bus),
                VehicleOption("মাইক্রো / টেম্পো", R.drawable.ic_micro),
                VehicleOption("অন্যান্য", R.drawable.ic_more)
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(vehicles) { vehicle ->
                    VehicleCard(
                        vehicle = vehicle,
                        isSelected = selectedVehicle == vehicle.name,
                        onClick = { selectedVehicle = vehicle.name }
                    )
                }
            }

            // 1. Fare Amount
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "আপনি কত ভাড়া দিয়েছেন?",
                fontFamily = BanglaFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Ink,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                color = CardWhite,
                border = BorderStroke(1.dp, FieldBorder)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "৳",
                        fontFamily = BanglaFamily,
                        fontSize = 20.sp,
                        color = Ink
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    BasicTextField(
                        value = fareAmount,
                        onValueChange = { fareAmount = it },
                        textStyle = TextStyle(fontFamily = BanglaFamily, fontSize = 14.sp, color = Ink),
                        modifier = Modifier.weight(1f),
                        cursorBrush = SolidColor(BrandGreen),
                        decorationBox = { innerTextField ->
                            if (fareAmount.isEmpty()) {
                                Text("পরিমাণ লিখুন", fontFamily = BanglaFamily, fontSize = 14.sp, color = FieldPlaceholder)
                            }
                            innerTextField()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            val quickAmounts = listOf("50", "60", "70", "80", "100+")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                quickAmounts.forEach { amount ->
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = CardWhite,
                        border = BorderStroke(1.dp, BrandGreen.copy(alpha = 0.2f)),
                        modifier = Modifier
                            .weight(1f)
                            .height(36.dp)
                            .clickable { fareAmount = amount }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = amount,
                                fontFamily = BanglaFamily,
                                fontSize = 13.sp,
                                color = BrandGreen,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            // 2. Time & Date
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "কখন যাত্রা করেছেন?",
                fontFamily = BanglaFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Ink,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = CardWhite,
                    border = BorderStroke(1.dp, FieldBorder),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .clickable { datePickerDialog.show() }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Outlined.CalendarToday, contentDescription = null, tint = InkMuted, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(selectedDateStr, fontFamily = BanglaFamily, fontSize = 13.sp, color = Ink, modifier = Modifier.weight(1f))
                        Icon(Icons.Outlined.KeyboardArrowDown, contentDescription = null, tint = InkMuted, modifier = Modifier.size(20.dp))
                    }
                }
                
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = CardWhite,
                    border = BorderStroke(1.dp, FieldBorder),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .clickable { timePickerDialog.show() }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Outlined.Schedule, contentDescription = null, tint = InkMuted, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(selectedTimeStr, fontFamily = BanglaFamily, fontSize = 13.sp, color = Ink, modifier = Modifier.weight(1f))
                        Icon(Icons.Outlined.KeyboardArrowDown, contentDescription = null, tint = InkMuted, modifier = Modifier.size(20.dp))
                    }
                }
            }

            // 3. Additional Note
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "অতিরিক্ত নোট (ঐচ্ছিক)",
                fontFamily = BanglaFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Ink,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .height(100.dp),
                shape = RoundedCornerShape(12.dp),
                color = CardWhite,
                border = BorderStroke(1.dp, FieldBorder)
            ) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)) {
                    BasicTextField(
                        value = noteText,
                        onValueChange = { if (it.length <= 100) noteText = it },
                        textStyle = TextStyle(fontFamily = BanglaFamily, fontSize = 13.sp, color = Ink),
                        modifier = Modifier.fillMaxSize(),
                        cursorBrush = SolidColor(BrandGreen),
                        decorationBox = { innerTextField ->
                            if (noteText.isEmpty()) {
                                Text("যেমন: বৃষ্টি ছিল, জ্যাম ছিল ইত্যাদি...", fontFamily = BanglaFamily, fontSize = 13.sp, color = FieldPlaceholder)
                            }
                            innerTextField()
                        }
                    )
                    Text(
                        text = "${noteText.length}/100",
                        fontFamily = BanglaFamily,
                        fontSize = 10.sp,
                        color = InkMuted,
                        modifier = Modifier.align(Alignment.BottomEnd)
                    )
                }
            }

            // 4. Proof Photo
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "প্রমাণ ছবি (ঐচ্ছিক)",
                fontFamily = BanglaFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Ink,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "ভাড়ার রসিদ বা স্ক্রিনশট আপলোড করুন",
                fontFamily = BanglaFamily,
                fontSize = 11.sp,
                color = InkMuted,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val dashColor = BrandGreen.copy(alpha = 0.5f)
                Box(
                    modifier = Modifier
                        .width(96.dp)
                        .height(96.dp)
                        .drawBehind {
                            drawRoundRect(
                                color = dashColor,
                                style = Stroke(
                                    width = 2.dp.toPx(),
                                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                                ),
                                cornerRadius = androidx.compose.ui.geometry.CornerRadius(12.dp.toPx())
                            )
                        }
                        .clickable { /* Handle photo upload */ },
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Outlined.PhotoCamera, contentDescription = null, tint = InkMuted, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("ছবি যোগ করুন", fontFamily = BanglaFamily, fontSize = 11.sp, color = Ink)
                    }
                }
                
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .height(96.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = BrandGreen.copy(alpha = 0.05f)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Icon(Icons.Outlined.Security, contentDescription = null, tint = BrandGreen, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "আপনার তথ্য গোপন রাখা হবে",
                                fontFamily = BanglaFamily,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Ink
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "আপনার নাম বা ব্যক্তিগত তথ্য অন্যদের কাছে প্রকাশ করা হবে না।",
                                fontFamily = BanglaFamily,
                                fontSize = 10.sp,
                                color = InkMuted,
                                lineHeight = 14.sp
                            )
                        }
                    }
                }
            }

            if (isImeVisible) {
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { /* Submit logic */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BrandGreen)
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ভাড়া জমা দিন",
                        fontFamily = BanglaFamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            } else {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    if (showHowItWorksBottomSheet) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(
            onDismissRequest = { showHowItWorksBottomSheet = false },
            sheetState = sheetState,
            containerColor = PageWhite,
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "কিভাবে কাজ করে?",
                    fontFamily = BanglaFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Ink,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                HowItWorksStep(
                    icon = Icons.Filled.LocationOn,
                    title = "রুট ও যান নির্বাচন",
                    description = "কোথা থেকে কোথায় গেছেন এবং কি যানে চড়েছেন তা নির্বাচন করুন।"
                )
                
                HowItWorksStep(
                    icon = Icons.Outlined.AccountBalanceWallet,
                    title = "ভাড়ার পরিমাণ",
                    description = "আপনি কত টাকা ভাড়া দিয়েছেন তা সঠিকভাবে উল্লেখ করুন।"
                )

                HowItWorksStep(
                    icon = Icons.Outlined.CalendarToday,
                    title = "যাত্রার সময়",
                    description = "কখন যাত্রা করেছেন তার সঠিক তারিখ ও সময় নির্বাচন করুন।"
                )

                HowItWorksStep(
                    icon = Icons.Outlined.Security,
                    title = "তথ্য যাচাই ও গোপনীয়তা",
                    description = "আপনার দেওয়া তথ্য অন্যান্য যাত্রীদের সাহায্য করবে এবং আপনার পরিচয় গোপন থাকবে।"
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Button(
                    onClick = { showHowItWorksBottomSheet = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BrandGreen)
                ) {
                    Text(
                        text = "বুঝতে পেরেছি",
                        fontFamily = BanglaFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 8.dp))
            }
        }
    }
}

@Composable
fun HowItWorksStep(icon: ImageVector, title: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.Top
    ) {
        Surface(
            shape = CircleShape,
            color = BrandGreen.copy(alpha = 0.1f),
            modifier = Modifier.size(36.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = BrandGreen,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontFamily = BanglaFamily,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Ink
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = description,
                fontFamily = BanglaFamily,
                fontSize = 11.sp,
                color = InkMuted,
                lineHeight = 16.sp
            )
        }
    }
}

data class VehicleOption(
    val name: String,
    val iconRes: Int
)

@Composable
fun VehicleCard(
    vehicle: VehicleOption,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor = if (isSelected) BrandGreen.copy(alpha = 0.05f) else CardWhite
    val borderColor = if (isSelected) BrandGreen else FieldBorder

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = bgColor,
        border = BorderStroke(1.dp, borderColor),
        modifier = modifier
            .width(88.dp)
            .height(84.dp)
            .clickable(onClick = onClick)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(4.dp)
        ) {
            Image(
                painter = painterResource(id = vehicle.iconRes),
                contentDescription = vehicle.name,
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = vehicle.name,
                fontFamily = BanglaFamily,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                color = Ink,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VaraDinScreenPreview() {
    AppTheme {
        VaraDinScreen(onNavigateBack = {})
    }
}
