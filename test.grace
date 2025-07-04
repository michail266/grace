fun main () : nothing
var a, b, c, result : int;
{
puts("Enter three numbers:\n");
puts("a: ");
a <- geti();
puts("b: ");
b <- geti();
puts("c: ");
c <- geti();

puts("Results:\n");
result <- a + b;
puts("a + b = ");
puti(result);
puts("\n");

result <- a * b;
puts("a * b = ");
puti(result);
puts("\n");

if a > b then {
puts("a is greater than b\n");
} else {
puts("a is not greater than b\n");
}

if c # 0 then {
result <- a div c;
puts("a / c = ");
puti(result);
puts("\n");
}

puts("Done!\n");
}