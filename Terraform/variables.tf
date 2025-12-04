variable "aws_region" {
  description = "La région AWS"
  type        = string
  default     = "us-east-1"
}

variable "cluster_name" {
  description = "Nom du cluster EKS"
  type        = string
  default     = "mykubernetes2"
}

variable "subnet_ids" {
  description = "IDs des sous-réseaux"
  type        = list(string)
  default     = [
    "subnet-0ce82f39cf86e0b08",
    "subnet-0d0b8f52d89ae3abc"
  ]
}

variable "role_arn" {
  description = "ARN du rôle IAM pour EKS"
  type        = string
  default     = "arn:aws:iam::992382676402:role/LabRole"
}

variable "vpc_id" {
  description = "L'ID du VPC pour le cluster EKS"
  type        = string
  default     = "vpc-0420028bd2a71bb23"
}

variable "vpc_cidr" {
  description = "CIDR block for the VPC"
  type        = string
  default     = "10.0.0.0/16"
}
