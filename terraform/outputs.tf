output "ec2_public_ip" {
  description = "Public IP of EC2 instance"
  value       = aws_instance.event_instance.public_ip
}

output "s3_bucket_name" {
  description = "S3 bucket name"
  value       = aws_s3_bucket.event_bucket.id
}
