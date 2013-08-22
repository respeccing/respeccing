//run with: go test

package demlinks

import "fmt"
import "testing"

func Test_reverseByte_LookupTable(t *testing.T) {
	fmt.Println("all numbers from 0 to 255 whose reverse is equal to itself:")
	b := new(byte)
	for i, ri := range reverse {
		if i == ri {
			*b = byte(i)
			FlipByte(b)
			fmt.Println(i, ri, reverse[i], *b)
			if (i != ri) || (ri != reverse[i]) || (byte(i) != *b) {
				t.Error("they weren't all the same")
				return
			}
		}
	}
}

func Test_reverseByte_func(*testing.T) {
	for i := 0; i <= 255; i++ {
		if byte(i) == reversef(byte(i)) {
			fmt.Println(i, reversef(byte(i)))
		}
	}
}

//inspired from: https://groups.google.com/d/msg/golang-nuts/q01oIascmiU/3z1G8Kdo9DsJ
func reversef(x byte) byte {
	x = (x&0x55)<<1 | (x&0xAA)>>1
	x = (x&0x33)<<2 | (x&0xCC)>>2
	x = (x&0x0F)<<4 | (x&0xF0)>>4
	return x
}

//inspired from: https://groups.google.com/d/msg/golang-nuts/q01oIascmiU/XOKX_hKqSNAJ
var reverse = [256]int{
	0, 128, 64, 192, 32, 160, 96, 224,
	16, 144, 80, 208, 48, 176, 112, 240,
	8, 136, 72, 200, 40, 168, 104, 232,
	24, 152, 88, 216, 56, 184, 120, 248,
	4, 132, 68, 196, 36, 164, 100, 228,
	20, 148, 84, 212, 52, 180, 116, 244,
	12, 140, 76, 204, 44, 172, 108, 236,
	28, 156, 92, 220, 60, 188, 124, 252,
	2, 130, 66, 194, 34, 162, 98, 226,
	18, 146, 82, 210, 50, 178, 114, 242,
	10, 138, 74, 202, 42, 170, 106, 234,
	26, 154, 90, 218, 58, 186, 122, 250,
	6, 134, 70, 198, 38, 166, 102, 230,
	22, 150, 86, 214, 54, 182, 118, 246,
	14, 142, 78, 206, 46, 174, 110, 238,
	30, 158, 94, 222, 62, 190, 126, 254,
	1, 129, 65, 193, 33, 161, 97, 225,
	17, 145, 81, 209, 49, 177, 113, 241,
	9, 137, 73, 201, 41, 169, 105, 233,
	25, 153, 89, 217, 57, 185, 121, 249,
	5, 133, 69, 197, 37, 165, 101, 229,
	21, 149, 85, 213, 53, 181, 117, 245,
	13, 141, 77, 205, 45, 173, 109, 237,
	29, 157, 93, 221, 61, 189, 125, 253,
	3, 131, 67, 195, 35, 163, 99, 227,
	19, 147, 83, 211, 51, 179, 115, 243,
	11, 139, 75, 203, 43, 171, 107, 235,
	27, 155, 91, 219, 59, 187, 123, 251,
	7, 135, 71, 199, 39, 167, 103, 231,
	23, 151, 87, 215, 55, 183, 119, 247,
	15, 143, 79, 207, 47, 175, 111, 239,
	31, 159, 95, 223, 63, 191, 127, 255,
}