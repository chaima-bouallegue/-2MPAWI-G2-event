variable "aws_region" {
  description = "AWS Region"
  type        = string
  default     = "us-east-1"
}

variable "cluster_name" {
  description = "EKS Cluster Name"
  type        = string
  default     = "mykubernetes"
}

variable "role_arn" {
  description = "IAM Role ARN for EKS"
  type        = string
  default     = "arn:aws:iam::862116348508:role/LabRole"
}

variable "vpc_id" {
  description = "VPC ID"
  type        = string
  default     = "vpc-0f9744da00d2ff58f"
}

variable "subnet_ids" {
  description = "Subnet IDs"
  type        = list(string)
  default     = [
    "subnet-01f676b40453c9420", # us-east-1c
    "subnet-071ccf1b469827d32", # us-east-1b
    "subnet-0707c1dcae13db5e0", # us-east-1d
    "subnet-0123437dc1c499180", # us-east-1f
    "subnet-00edba30ded827d0e"  # us-east-1a
  ]
}
