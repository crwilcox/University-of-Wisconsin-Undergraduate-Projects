// Library - ece555_projectlib, Cell - shifter, View - schematic
// LAST TIME SAVED: Dec  8 17:06:05 2010
// NETLIST TIME: Dec  8 17:06:47 2010
`timescale 1ns / 1ns 

module shifter ( Z, A, B, left_right, shift_en );


input  left_right, shift_en;

output [31:0]  Z;

input [31:0]  B;
input [31:0]  A;

// Buses in the design

wire  [0:31]  B_16;

wire  [0:31]  B_1;

wire  [0:31]  B_8;

wire  [0:31]  B_4;

wire  [0:31]  B_2;


specify 
    specparam CDS_LIBNAME  = "ece555_projectlib";
    specparam CDS_CELLNAME = "shifter";
    specparam CDS_VIEWNAME = "schematic";
endspecify

shift_1bit I159 ( net01821, B_1[15], left2, noshift2, right2, B_1[13],
     B_1[17]);
shift_1bit I158 ( net01819, B_1[14], left2, noshift2, right2, B_1[12],
     B_1[16]);
shift_1bit I157 ( net01815, B_1[12], left2, noshift2, right2, B_1[10],
     B_1[14]);
shift_1bit I156 ( net01817, B_1[13], left2, noshift2, right2, B_1[11],
     B_1[15]);
shift_1bit I155 ( net01809, B_1[9], left2, noshift2, right2, B_1[7],
     B_1[11]);
shift_1bit I154 ( net01807, B_1[8], left2, noshift2, right2, B_1[6],
     B_1[10]);
shift_1bit I153 ( net01811, B_1[10], left2, noshift2, right2, B_1[8],
     B_1[12]);
shift_1bit I152 ( net01813, B_1[11], left2, noshift2, right2, B_1[9],
     B_1[13]);
shift_1bit I151 ( net01751, B_2[11], left4, noshift4, right4, B_2[7],
     B_2[15]);
shift_1bit I150 ( net01749, B_2[10], left4, noshift4, right4, B_2[6],
     B_2[14]);
shift_1bit I149 ( net01745, B_2[8], left4, noshift4, right4, B_2[4],
     B_2[12]);
shift_1bit I148 ( net01747, B_2[9], left4, noshift4, right4, B_2[5],
     B_2[13]);
shift_1bit I147 ( net01755, B_2[13], left4, noshift4, right4, B_2[9],
     B_2[17]);
shift_1bit I146 ( net01753, B_2[12], left4, noshift4, right4, B_2[8],
     B_2[16]);
shift_1bit I145 ( net01757, B_2[14], left4, noshift4, right4, B_2[10],
     B_2[18]);
shift_1bit I144 ( net01759, B_2[15], left4, noshift4, right4, B_2[11],
     B_2[19]);
shift_1bit I143 ( net01689, B_4[11], left8, noshift8, right8, B_4[3],
     B_4[19]);
shift_1bit I142 ( net01687, B_4[10], left8, noshift8, right8, B_4[2],
     B_4[18]);
shift_1bit I141 ( net01683, B_4[8], left8, noshift8, right8, B_4[0],
     B_4[16]);
shift_1bit I140 ( net01685, B_4[9], left8, noshift8, right8, B_4[1],
     B_4[17]);
shift_1bit I139 ( net01693, B_4[13], left8, noshift8, right8, B_4[5],
     B_4[21]);
shift_1bit I138 ( net01691, B_4[12], left8, noshift8, right8, B_4[4],
     B_4[20]);
shift_1bit I137 ( net01695, B_4[14], left8, noshift8, right8, B_4[6],
     B_4[22]);
shift_1bit I136 ( net01697, B_4[15], left8, noshift8, right8, B_4[7],
     B_4[23]);
shift_1bit I135 ( B_16[15], B_8[15], left16, noshift16, right16,
     1, B_8[31]);
shift_1bit I134 ( B_16[14], B_8[14], left16, noshift16, right16,
     1, B_8[30]);
shift_1bit I133 ( B_16[12], B_8[12], left16, noshift16, right16,
     1, B_8[28]);
shift_1bit I132 ( B_16[13], B_8[13], left16, noshift16, right16,
     1, B_8[29]);
shift_1bit I131 ( B_16[9], B_8[9], left16, noshift16, right16,
     1, B_8[25]);
shift_1bit I130 ( B_16[8], B_8[8], left16, noshift16, right16,
     1, B_8[24]);
shift_1bit I129 ( B_16[10], B_8[10], left16, noshift16, right16,
     1, B_8[26]);
shift_1bit I128 ( B_16[11], B_8[11], left16, noshift16, right16,
     1, B_8[27]);
shift_1bit I127 ( B_16[3], B_8[3], left16, noshift16, right16,
     1, B_8[19]);
shift_1bit I126 ( B_16[2], B_8[2], left16, noshift16, right16,
     1, B_8[18]);
shift_1bit I125 ( B_16[0], B_8[0], left16, noshift16, right16,
     1, B_8[16]);
shift_1bit I124 ( B_16[1], B_8[1], left16, noshift16, right16,
     1, B_8[17]);
shift_1bit I123 ( B_16[5], B_8[5], left16, noshift16, right16,
     1, B_8[21]);
shift_1bit I122 ( B_16[4], B_8[4], left16, noshift16, right16,
     1, B_8[20]);
shift_1bit I121 ( B_16[6], B_8[6], left16, noshift16, right16,
     1, B_8[22]);
shift_1bit I120 ( B_16[7], B_8[7], left16, noshift16, right16,
     1, B_8[23]);
shift_1bit I119 ( net01681, B_4[7], left8, noshift8, right8,
     0, B_4[15]);
shift_1bit I118 ( net01679, B_4[6], left8, noshift8, right8,
     0, B_4[14]);
shift_1bit I117 ( net01675, B_4[4], left8, noshift8, right8,
     0, B_4[12]);
shift_1bit I116 ( net01677, B_4[5], left8, noshift8, right8,
     0, B_4[13]);
shift_1bit I115 ( net01669, B_4[1], left8, noshift8, right8,
     0, B_4[9]);
shift_1bit I114 ( net01667, B_4[0], left8, noshift8, right8,
     0, B_4[8]);
shift_1bit I113 ( net01671, B_4[2], left8, noshift8, right8,
     0, B_4[10]);
shift_1bit I112 ( net01673, B_4[3], left8, noshift8, right8,
     0, B_4[11]);
shift_1bit I111 ( net01743, B_2[7], left4, noshift4, right4, B_2[3],
     B_2[11]);
shift_1bit I110 ( net01741, B_2[6], left4, noshift4, right4, B_2[2],
     B_2[10]);
shift_1bit I109 ( net01737, B_2[4], left4, noshift4, right4, B_2[0],
     B_2[8]);
shift_1bit I108 ( net01739, B_2[5], left4, noshift4, right4, B_2[1],
     B_2[9]);
shift_1bit I107 ( net01731, B_2[1], left4, noshift4, right4,
     1, B_2[5]);
shift_1bit I106 ( net01729, B_2[0], left4, noshift4, right4,
     1, B_2[4]);
shift_1bit I105 ( net01733, B_2[2], left4, noshift4, right4,
     1, B_2[6]);
shift_1bit I104 ( net01735, B_2[3], left4, noshift4, right4,
     1, B_2[7]);
shift_1bit I103 ( net01797, B_1[3], left2, noshift2, right2, B_1[1],
     B_1[5]);
shift_1bit I102 ( net01795, B_1[2], left2, noshift2, right2, B_1[0],
     B_1[4]);
shift_1bit I101 ( net01791, B_1[0], left2, noshift2, right2,
     0, B_1[2]);
shift_1bit I100 ( net01793, B_1[1], left2, noshift2, right2,
     0, B_1[3]);
shift_1bit I99 ( net01801, B_1[5], left2, noshift2, right2, B_1[3],
     B_1[7]);
shift_1bit I98 ( net01799, B_1[4], left2, noshift2, right2, B_1[2],
     B_1[6]);
shift_1bit I97 ( net01803, B_1[6], left2, noshift2, right2, B_1[4],
     B_1[8]);
shift_1bit I96 ( net01805, B_1[7], left2, noshift2, right2, B_1[5],
     B_1[9]);
shift_1bit I95 ( net01889, net01912, left1, noshift1, right1, net02973,
     net02977);
shift_1bit I94 ( net01887, net02973, left1, noshift1, right1, net02945,
     net01912);
shift_1bit I93 ( net01883, net02971, left1, noshift1, right1, net01555,
     net02945);
shift_1bit I92 ( net01885, net02945, left1, noshift1, right1, net02971,
     net02973);
shift_1bit I91 ( net01877, net02965, left1, noshift1, right1, net01569,
     net01590);
shift_1bit I90 ( net01875, net01569, left1, noshift1, right1, net01576,
     net02965);
shift_1bit I89 ( net01879, net01590, left1, noshift1, right1, net02965,
     net01555);
shift_1bit I88 ( net01881, net01555, left1, noshift1, right1, net01590,
     net02971);
shift_1bit I87 ( net01865, net01089, left1, noshift1, right1, net02947,
     net01126);
shift_1bit I86 ( net01863, net02947, left1, noshift1, right1, net01604,
     net01089);
shift_1bit I85 ( net01859, net01086, left1, noshift1, right1,
     1, net01604);
shift_1bit I84 ( net01861, net01604, left1, noshift1, right1, net01086,
     net02947);
shift_1bit I83 ( net01869, net02959, left1, noshift1, right1, net01126,
     net02961);
shift_1bit I82 ( net01867, net01126, left1, noshift1, right1, net01089,
     net02959);
shift_1bit I81 ( net01871, net02961, left1, noshift1, right1, net02959,
     net01576);
shift_1bit I80 ( net01873, net01576, left1, noshift1, right1, net02961,
     net01569);
shift_1bit I79 ( net01837, B_1[23], left2, noshift2, right2, B_1[21],
     B_1[25]);
shift_1bit I78 ( net01835, B_1[22], left2, noshift2, right2, B_1[20],
     B_1[24]);
shift_1bit I77 ( net01831, B_1[20], left2, noshift2, right2, B_1[18],
     B_1[22]);
shift_1bit I76 ( net01833, B_1[21], left2, noshift2, right2, B_1[19],
     B_1[23]);
shift_1bit I75 ( net01825, B_1[17], left2, noshift2, right2, B_1[15],
     B_1[19]);
shift_1bit I74 ( net01823, B_1[16], left2, noshift2, right2, B_1[14],
     B_1[18]);
shift_1bit I73 ( net01827, B_1[18], left2, noshift2, right2, B_1[16],
     B_1[20]);
shift_1bit I72 ( net01829, B_1[19], left2, noshift2, right2, B_1[17],
     B_1[21]);
shift_1bit I71 ( net01767, B_2[19], left4, noshift4, right4, B_2[15],
     B_2[23]);
shift_1bit I70 ( net01765, B_2[18], left4, noshift4, right4, B_2[14],
     B_2[22]);
shift_1bit I69 ( net01761, B_2[16], left4, noshift4, right4, B_2[12],
     B_2[20]);
shift_1bit I68 ( net01763, B_2[17], left4, noshift4, right4, B_2[13],
     B_2[21]);
shift_1bit I67 ( net01771, B_2[21], left4, noshift4, right4, B_2[17],
     B_2[25]);
shift_1bit I66 ( net01769, B_2[20], left4, noshift4, right4, B_2[16],
     B_2[24]);
shift_1bit I65 ( net01773, B_2[22], left4, noshift4, right4, B_2[18],
     B_2[26]);
shift_1bit I64 ( net01775, B_2[23], left4, noshift4, right4, B_2[19],
     B_2[27]);
shift_1bit I63 ( net01705, B_4[19], left8, noshift8, right8, B_4[11],
     B_4[27]);
shift_1bit I62 ( net01703, B_4[18], left8, noshift8, right8, B_4[10],
     B_4[26]);
shift_1bit I61 ( net01699, B_4[16], left8, noshift8, right8, B_4[8],
     B_4[24]);
shift_1bit I60 ( net01701, B_4[17], left8, noshift8, right8, B_4[9],
     B_4[25]);
shift_1bit I59 ( net01709, B_4[21], left8, noshift8, right8, B_4[13],
     B_4[29]);
shift_1bit I58 ( net01707, B_4[20], left8, noshift8, right8, B_4[12],
     B_4[28]);
shift_1bit I57 ( net01711, B_4[22], left8, noshift8, right8, B_4[14],
     B_4[30]);
shift_1bit I56 ( net01713, B_4[23], left8, noshift8, right8, B_4[15],
     B_4[31]);
shift_1bit I55 ( B_16[23], B_8[23], left16, noshift16, right16, B_8[7],
     B_8[31]);
shift_1bit I54 ( B_16[22], B_8[22], left16, noshift16, right16, B_8[6],
     B_8[31]);
shift_1bit I53 ( B_16[20], B_8[20], left16, noshift16, right16, B_8[4],
     B_8[31]);
shift_1bit I52 ( B_16[21], B_8[21], left16, noshift16, right16, B_8[5],
     B_8[31]);
shift_1bit I51 ( B_16[17], B_8[17], left16, noshift16, right16, B_8[1],
     B_8[31]);
shift_1bit I50 ( B_16[16], B_8[16], left16, noshift16, right16, B_8[0],
     B_8[31]);
shift_1bit I49 ( B_16[18], B_8[18], left16, noshift16, right16, B_8[2],
     B_8[31]);
shift_1bit I48 ( B_16[19], B_8[19], left16, noshift16, right16, B_8[3],
     B_8[31]);
shift_1bit I47 ( net01905, net02991, left1, noshift1, right1, net02989,
     net02150);
shift_1bit I46 ( net01903, net02989, left1, noshift1, right1, net02987,
     net02991);
shift_1bit I45 ( net01899, net02985, left1, noshift1, right1, net02983,
     net02987);
shift_1bit I44 ( net01901, net02987, left1, noshift1, right1, net02985,
     net02989);
shift_1bit I43 ( net01893, net01258, left1, noshift1, right1, net02977,
     net01926);
shift_1bit I42 ( net01891, net02977, left1, noshift1, right1, net01912,
     net01258);
shift_1bit I41 ( net01895, net01926, left1, noshift1, right1, net01258,
     net02983);
shift_1bit I40 ( net01897, net02983, left1, noshift1, right1, net01926,
     net02985);
shift_1bit I39 ( B_16[27], B_8[27], left16, noshift16, right16,
     B_8[11], B_8[31]);
shift_1bit I38 ( B_16[26], B_8[26], left16, noshift16, right16,
     B_8[10], B_8[31]);
shift_1bit I37 ( B_16[24], B_8[24], left16, noshift16, right16, B_8[8],
     B_8[31]);
shift_1bit I36 ( B_16[25], B_8[25], left16, noshift16, right16, B_8[9],
     B_8[31]);
shift_1bit I35 ( B_16[29], B_8[29], left16, noshift16, right16,
     B_8[13], B_8[31]);
shift_1bit I34 ( B_16[28], B_8[28], left16, noshift16, right16,
     B_8[12], B_8[31]);
shift_1bit I33 ( B_16[30], B_8[30], left16, noshift16, right16,
     B_8[14], B_8[31]);
shift_1bit I32 ( B_16[31], B_8[31], left16, noshift16, right16,
     B_8[15], B_8[31]);
shift_1bit I31 ( net01853, B_4[31], left8, noshift8, right8, B_4[23],
     B_4[31]);
shift_1bit I30 ( net01727, B_4[30], left8, noshift8, right8, B_4[22],
     B_4[31]);
shift_1bit I29 ( net01723, B_4[28], left8, noshift8, right8, B_4[20],
     B_4[31]);
shift_1bit I28 ( net01725, B_4[29], left8, noshift8, right8, B_4[21],
     B_4[31]);
shift_1bit I27 ( net01717, B_4[25], left8, noshift8, right8, B_4[17],
     B_4[31]);
shift_1bit I26 ( net01715, B_4[24], left8, noshift8, right8, B_4[16],
     B_4[31]);
shift_1bit I25 ( net01719, B_4[26], left8, noshift8, right8, B_4[18],
     B_4[31]);
shift_1bit I24 ( net01721, B_4[27], left8, noshift8, right8, B_4[19],
     B_4[31]);
shift_1bit I23 ( net01855, B_2[31], left4, noshift4, right4, B_2[27],
     B_2[31]);
shift_1bit I22 ( net01789, B_2[30], left4, noshift4, right4, B_2[26],
     B_2[31]);
shift_1bit I21 ( net01785, B_2[28], left4, noshift4, right4, B_2[24],
     B_2[31]);
shift_1bit I20 ( net01787, B_2[29], left4, noshift4, right4, B_2[25],
     B_2[31]);
shift_1bit I19 ( net01779, B_2[25], left4, noshift4, right4, B_2[21],
     B_2[29]);
shift_1bit I18 ( net01777, B_2[24], left4, noshift4, right4, B_2[20],
     B_2[28]);
shift_1bit I17 ( net01781, B_2[26], left4, noshift4, right4, B_2[22],
     B_2[30]);
shift_1bit I16 ( net01783, B_2[27], left4, noshift4, right4, B_2[23],
     B_2[31]);
shift_1bit I15 ( net01845, B_1[27], left2, noshift2, right2, B_1[25],
     B_1[29]);
shift_1bit I14 ( net01843, B_1[26], left2, noshift2, right2, B_1[24],
     B_1[28]);
shift_1bit I13 ( net01839, B_1[24], left2, noshift2, right2, B_1[22],
     B_1[26]);
shift_1bit I12 ( net01841, B_1[25], left2, noshift2, right2, B_1[23],
     B_1[27]);
shift_1bit I11 ( net01913, net02999, left1, noshift1, right1, net02997,
     net03001);
shift_1bit I10 ( net01911, net02997, left1, noshift1, right1, net02136,
     net02999);
shift_1bit I7 ( net01849, B_1[29], left2, noshift2, right2, B_1[27],
     B_1[31]);
shift_1bit I8 ( net01909, net02136, left1, noshift1, right1, net02150,
     net02997);
shift_1bit I2 ( net01919, net01503, left1, noshift1, right1, net03003,
     net03007);
shift_1bit I3 ( net01851, B_1[30], left2, noshift2, right2, B_1[28],
     B_1[31]);
shift_1bit I9 ( net01907, net02150, left1, noshift1, right1, net02991,
     net02136);
shift_1bit I4 ( net01915, net03001, left1, noshift1, right1, net02999,
     net03003);
shift_1bit I5 ( net01917, net03003, left1, noshift1, right1, net03001,
     net01503);
shift_1bit I6 ( net01847, B_1[28], left2, noshift2, right2, B_1[26],
     B_1[30]);
shift_1bit I0 ( net01921, net03007, left1, noshift1, right1, net01503,
     net03007);
shift_1bit I1 ( net01857, B_1[31], left2, noshift2, right2, B_1[29],
     B_1[31]);
nmos N100 ( shift_en_n, 0, shift_en);
nmos N9 ( left_right_n, 0, left_right);
pmos P65 ( shift_en_n, 1, shift_en);
pmos P0 ( left_right_n, 1, left_right);
shift_inv I469 ( net02973, B[14]);
shift_inv I482 ( net01604, B[1]);
shift_inv I481 ( net02947, B[2]);
shift_inv I480 ( net01089, B[3]);
shift_inv I479 ( net01126, B[4]);
shift_inv I478 ( net02959, B[5]);
shift_inv I477 ( net02961, B[6]);
shift_inv I476 ( net01576, B[7]);
shift_inv I475 ( net01569, B[8]);
shift_inv I474 ( net02965, B[9]);
shift_inv I473 ( net01590, B[10]);
shift_inv I472 ( net01555, B[11]);
shift_inv I471 ( net02971, B[12]);
shift_inv I562 ( net04656, B_16[17]);
shift_inv I557 ( net02783, B_16[22]);
shift_inv I489 ( net02811, B_16[29]);
shift_inv I555 ( net02905, B_16[24]);
shift_inv I554 ( net02795, B_16[25]);
shift_inv I556 ( net02787, B_16[23]);
shift_inv I549 ( net04589, B_16[30]);
shift_inv I553 ( net02799, B_16[26]);
shift_inv I552 ( net02803, B_16[27]);
shift_inv I548 ( net04664, B_16[31]);
shift_inv I514 ( net04485, B_16[16]);
shift_inv I510 ( net04584, B_16[18]);
shift_inv I551 ( net02620, B_16[28]);
shift_inv I518 ( net04481, B_16[14]);
shift_inv I517 ( net04648, B_16[15]);
shift_inv I521 ( net02861, B_16[13]);
shift_inv I522 ( net04477, B_16[12]);
shift_inv I526 ( net04473, B_16[10]);
shift_inv I525 ( net04632, B_16[11]);
shift_inv I530 ( net04620, B_16[8]);
shift_inv I529 ( net04471, B_16[9]);
shift_inv I537 ( net04608, B_16[5]);
shift_inv I533 ( net04616, B_16[7]);
shift_inv I538 ( net04461, B_16[4]);
shift_inv I534 ( net04465, B_16[6]);
shift_inv I542 ( net04457, B_16[2]);
shift_inv I546 ( net02564, B_16[0]);
shift_inv I545 ( net04592, B_16[1]);
shift_inv I467 ( net02977, B[16]);
shift_inv I560 ( net04660, B_16[19]);
shift_inv I559 ( net01080, B_16[20]);
shift_inv I558 ( net02779, B_16[21]);
shift_inv I468 ( net01912, B[15]);
shift_inv I470 ( net02945, B[13]);
shift_inv I483 ( net01086, B[0]);
shift_inv I455 ( net03001, B[28]);
shift_inv I454 ( net03003, B[29]);
shift_inv I453 ( net01503, B[30]);
shift_inv I457 ( net02997, B[26]);
shift_inv I459 ( net02150, B[24]);
shift_inv I452 ( net03007, B[31]);
shift_inv I461 ( net02989, B[22]);
shift_inv I458 ( net02136, B[25]);
shift_inv I456 ( net02999, B[27]);
shift_inv I576 ( net04600, B_16[3]);
shift_inv I464 ( net02983, B[19]);
shift_inv I462 ( net02987, B[21]);
shift_inv I463 ( net02985, B[20]);
shift_inv I460 ( net02991, B[23]);
shift_inv I465 ( net01926, B[18]);
shift_inv I466 ( net01258, B[17]);
shift_pass I487 ( Z[30], net04589, shift_en, shift_en_n);
shift_pass I488 ( Z[29], net02811, shift_en, shift_en_n);
shift_pass I492 ( Z[27], net02803, shift_en, shift_en_n);
shift_pass I491 ( Z[28], net02620, shift_en, shift_en_n);
shift_pass I496 ( Z[25], net02795, shift_en, shift_en_n);
shift_pass I495 ( Z[26], net02799, shift_en, shift_en_n);
shift_pass I500 ( Z[23], net02787, shift_en, shift_en_n);
shift_pass I499 ( Z[24], net02905, shift_en, shift_en_n);
shift_pass I504 ( Z[21], net02779, shift_en, shift_en_n);
shift_pass I508 ( Z[19], net04660, shift_en, shift_en_n);
shift_pass I507 ( Z[20], net01080, shift_en, shift_en_n);
shift_pass I503 ( Z[22], net02783, shift_en, shift_en_n);
shift_pass I512 ( Z[17], net04656, shift_en, shift_en_n);
shift_pass I511 ( Z[18], net04584, shift_en, shift_en_n);
shift_pass I516 ( Z[15], net04648, shift_en, shift_en_n);
shift_pass I515 ( Z[16], net04485, shift_en, shift_en_n);
shift_pass I519 ( Z[14], net04481, shift_en, shift_en_n);
shift_pass I520 ( Z[13], net02861, shift_en, shift_en_n);
shift_pass I524 ( Z[11], net04632, shift_en, shift_en_n);
shift_pass I523 ( Z[12], net04477, shift_en, shift_en_n);
shift_pass I527 ( Z[10], net04473, shift_en, shift_en_n);
shift_pass I528 ( Z[9], net04471, shift_en, shift_en_n);
shift_pass I532 ( Z[7], net04616, shift_en, shift_en_n);
shift_pass I531 ( Z[8], net04620, shift_en, shift_en_n);
shift_pass I536 ( Z[5], net04608, shift_en, shift_en_n);
shift_pass I540 ( Z[3], net04600, shift_en, shift_en_n);
shift_pass I539 ( Z[4], net04461, shift_en, shift_en_n);
shift_pass I535 ( Z[6], net04465, shift_en, shift_en_n);
shift_pass I543 ( Z[2], net04457, shift_en, shift_en_n);
shift_pass I544 ( Z[1], net04592, shift_en, shift_en_n);
shift_pass I547 ( Z[0], net02564, shift_en, shift_en_n);
shift_pass I484 ( Z[31], net04664, shift_en, shift_en_n);
shift_signals I584 ( left8, noshift8, right8, A[3], left_right,
     left_right_n);
shift_signals I583 ( left4, noshift4, right4, A[2], left_right,
     left_right_n);
shift_signals I582 ( left2, noshift2, right2, A[1], left_right,
     left_right_n);
shift_signals I585 ( left16, noshift16, right16, A[4], left_right,
     left_right_n);
shift_signals I581 ( left1, noshift1, right1, A[0], left_right,
     left_right_n);
shift_inv_signals I806 ( B_8[0], net01667);
shift_inv_signals I805 ( B_8[1], net01669);
shift_inv_signals I804 ( B_8[2], net01671);
shift_inv_signals I803 ( B_8[3], net01673);
shift_inv_signals I802 ( B_8[4], net01675);
shift_inv_signals I801 ( B_8[5], net01677);
shift_inv_signals I800 ( B_8[6], net01679);
shift_inv_signals I799 ( B_8[7], net01681);
shift_inv_signals I798 ( B_8[8], net01683);
shift_inv_signals I797 ( B_8[9], net01685);
shift_inv_signals I796 ( B_8[10], net01687);
shift_inv_signals I795 ( B_8[11], net01689);
shift_inv_signals I794 ( B_8[12], net01691);
shift_inv_signals I793 ( B_8[13], net01693);
shift_inv_signals I792 ( B_8[14], net01695);
shift_inv_signals I791 ( B_8[15], net01697);
shift_inv_signals I790 ( B_8[16], net01699);
shift_inv_signals I789 ( B_8[17], net01701);
shift_inv_signals I788 ( B_8[18], net01703);
shift_inv_signals I787 ( B_8[19], net01705);
shift_inv_signals I786 ( B_8[20], net01707);
shift_inv_signals I785 ( B_8[21], net01709);
shift_inv_signals I784 ( B_8[22], net01711);
shift_inv_signals I783 ( B_8[23], net01713);
shift_inv_signals I782 ( B_8[24], net01715);
shift_inv_signals I781 ( B_8[25], net01717);
shift_inv_signals I780 ( B_8[26], net01719);
shift_inv_signals I779 ( B_8[27], net01721);
shift_inv_signals I778 ( B_8[28], net01723);
shift_inv_signals I777 ( B_8[29], net01725);
shift_inv_signals I776 ( B_8[30], net01727);
shift_inv_signals I775 ( B_4[0], net01729);
shift_inv_signals I774 ( B_4[1], net01731);
shift_inv_signals I773 ( B_4[2], net01733);
shift_inv_signals I772 ( B_4[3], net01735);
shift_inv_signals I771 ( B_4[4], net01737);
shift_inv_signals I770 ( B_4[5], net01739);
shift_inv_signals I769 ( B_4[6], net01741);
shift_inv_signals I768 ( B_4[7], net01743);
shift_inv_signals I767 ( B_4[8], net01745);
shift_inv_signals I766 ( B_4[9], net01747);
shift_inv_signals I765 ( B_4[10], net01749);
shift_inv_signals I764 ( B_4[11], net01751);
shift_inv_signals I763 ( B_4[12], net01753);
shift_inv_signals I762 ( B_4[13], net01755);
shift_inv_signals I761 ( B_4[14], net01757);
shift_inv_signals I760 ( B_4[15], net01759);
shift_inv_signals I759 ( B_4[16], net01761);
shift_inv_signals I758 ( B_4[17], net01763);
shift_inv_signals I757 ( B_4[18], net01765);
shift_inv_signals I756 ( B_4[19], net01767);
shift_inv_signals I755 ( B_4[20], net01769);
shift_inv_signals I754 ( B_4[21], net01771);
shift_inv_signals I753 ( B_4[22], net01773);
shift_inv_signals I752 ( B_4[23], net01775);
shift_inv_signals I751 ( B_4[24], net01777);
shift_inv_signals I750 ( B_4[25], net01779);
shift_inv_signals I749 ( B_4[26], net01781);
shift_inv_signals I748 ( B_4[27], net01783);
shift_inv_signals I747 ( B_4[28], net01785);
shift_inv_signals I746 ( B_4[29], net01787);
shift_inv_signals I745 ( B_4[30], net01789);
shift_inv_signals I744 ( B_2[0], net01791);
shift_inv_signals I743 ( B_2[1], net01793);
shift_inv_signals I742 ( B_2[2], net01795);
shift_inv_signals I741 ( B_2[3], net01797);
shift_inv_signals I740 ( B_2[4], net01799);
shift_inv_signals I739 ( B_2[5], net01801);
shift_inv_signals I738 ( B_2[6], net01803);
shift_inv_signals I737 ( B_2[7], net01805);
shift_inv_signals I736 ( B_2[8], net01807);
shift_inv_signals I735 ( B_2[9], net01809);
shift_inv_signals I734 ( B_2[10], net01811);
shift_inv_signals I733 ( B_2[11], net01813);
shift_inv_signals I732 ( B_2[12], net01815);
shift_inv_signals I731 ( B_2[13], net01817);
shift_inv_signals I730 ( B_2[14], net01819);
shift_inv_signals I729 ( B_2[15], net01821);
shift_inv_signals I728 ( B_2[16], net01823);
shift_inv_signals I727 ( B_2[17], net01825);
shift_inv_signals I726 ( B_2[18], net01827);
shift_inv_signals I725 ( B_2[19], net01829);
shift_inv_signals I724 ( B_2[20], net01831);
shift_inv_signals I723 ( B_2[21], net01833);
shift_inv_signals I722 ( B_2[22], net01835);
shift_inv_signals I721 ( B_2[23], net01837);
shift_inv_signals I720 ( B_2[24], net01839);
shift_inv_signals I719 ( B_2[25], net01841);
shift_inv_signals I718 ( B_2[26], net01843);
shift_inv_signals I717 ( B_2[27], net01845);
shift_inv_signals I716 ( B_2[28], net01847);
shift_inv_signals I715 ( B_2[29], net01849);
shift_inv_signals I714 ( B_2[30], net01851);
shift_inv_signals I713 ( B_8[31], net01853);
shift_inv_signals I650 ( B_4[31], net01855);
shift_inv_signals I618 ( B_2[31], net01857);
shift_inv_signals I617 ( B_1[0], net01859);
shift_inv_signals I616 ( B_1[1], net01861);
shift_inv_signals I615 ( B_1[2], net01863);
shift_inv_signals I614 ( B_1[3], net01865);
shift_inv_signals I613 ( B_1[4], net01867);
shift_inv_signals I612 ( B_1[5], net01869);
shift_inv_signals I611 ( B_1[6], net01871);
shift_inv_signals I610 ( B_1[7], net01873);
shift_inv_signals I609 ( B_1[8], net01875);
shift_inv_signals I608 ( B_1[9], net01877);
shift_inv_signals I607 ( B_1[10], net01879);
shift_inv_signals I606 ( B_1[11], net01881);
shift_inv_signals I605 ( B_1[12], net01883);
shift_inv_signals I604 ( B_1[13], net01885);
shift_inv_signals I603 ( B_1[14], net01887);
shift_inv_signals I602 ( B_1[15], net01889);
shift_inv_signals I601 ( B_1[16], net01891);
shift_inv_signals I600 ( B_1[17], net01893);
shift_inv_signals I599 ( B_1[18], net01895);
shift_inv_signals I598 ( B_1[19], net01897);
shift_inv_signals I597 ( B_1[20], net01899);
shift_inv_signals I596 ( B_1[21], net01901);
shift_inv_signals I595 ( B_1[22], net01903);
shift_inv_signals I594 ( B_1[23], net01905);
shift_inv_signals I593 ( B_1[24], net01907);
shift_inv_signals I592 ( B_1[25], net01909);
shift_inv_signals I591 ( B_1[26], net01911);
shift_inv_signals I590 ( B_1[27], net01913);
shift_inv_signals I589 ( B_1[28], net01915);
shift_inv_signals I588 ( B_1[29], net01917);
shift_inv_signals I587 ( B_1[30], net01919);
shift_inv_signals I586 ( B_1[31], net01921);

endmodule
