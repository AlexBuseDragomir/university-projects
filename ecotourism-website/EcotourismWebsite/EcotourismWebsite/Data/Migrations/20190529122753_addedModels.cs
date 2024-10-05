using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace EcotourismWebsite.Data.Migrations
{
    public partial class addedModels : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "TripCategory",
                columns: table => new
                {
                    TripCategoryId = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn),
                    TripCategoryName = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_TripCategory", x => x.TripCategoryId);
                });

            migrationBuilder.CreateTable(
                name: "Trip",
                columns: table => new
                {
                    TripId = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn),
                    Title = table.Column<string>(type: "nvarchar(100)", nullable: true),
                    TotalPrice = table.Column<int>(nullable: false),
                    Location = table.Column<string>(type: "nvarchar(100)", nullable: true),
                    DateAdded = table.Column<DateTime>(nullable: false),
                    Description = table.Column<string>(type: "nvarchar(3000)", nullable: true),
                    TripGuideId1 = table.Column<string>(nullable: true),
                    TripGuideId = table.Column<int>(nullable: false),
                    TripCategoryId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Trip", x => x.TripId);
                    table.ForeignKey(
                        name: "FK_Trip_TripCategory_TripCategoryId",
                        column: x => x.TripCategoryId,
                        principalTable: "TripCategory",
                        principalColumn: "TripCategoryId",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_Trip_AspNetUsers_TripGuideId1",
                        column: x => x.TripGuideId1,
                        principalTable: "AspNetUsers",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateTable(
                name: "Comment",
                columns: table => new
                {
                    CommentId = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn),
                    Text = table.Column<string>(type: "nvarchar(500)", nullable: true),
                    Date = table.Column<DateTime>(nullable: false),
                    PostDate = table.Column<DateTime>(nullable: false),
                    UserId1 = table.Column<string>(nullable: true),
                    UserId = table.Column<int>(nullable: true),
                    TripId = table.Column<int>(nullable: false),
                    TripGuideId1 = table.Column<string>(nullable: true),
                    TripGuideId = table.Column<int>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Comment", x => x.CommentId);
                    table.ForeignKey(
                        name: "FK_Comment_AspNetUsers_TripGuideId1",
                        column: x => x.TripGuideId1,
                        principalTable: "AspNetUsers",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_Comment_Trip_TripId",
                        column: x => x.TripId,
                        principalTable: "Trip",
                        principalColumn: "TripId",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_Comment_AspNetUsers_UserId1",
                        column: x => x.UserId1,
                        principalTable: "AspNetUsers",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateTable(
                name: "TripReservation",
                columns: table => new
                {
                    TripReservationId = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn),
                    ReservationDate = table.Column<DateTime>(nullable: false),
                    TripDate = table.Column<DateTime>(nullable: false),
                    TripId = table.Column<int>(nullable: false),
                    UserId1 = table.Column<string>(nullable: true),
                    UserId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_TripReservation", x => x.TripReservationId);
                    table.ForeignKey(
                        name: "FK_TripReservation_Trip_TripId",
                        column: x => x.TripId,
                        principalTable: "Trip",
                        principalColumn: "TripId",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_TripReservation_AspNetUsers_UserId1",
                        column: x => x.UserId1,
                        principalTable: "AspNetUsers",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_Comment_TripGuideId1",
                table: "Comment",
                column: "TripGuideId1");

            migrationBuilder.CreateIndex(
                name: "IX_Comment_TripId",
                table: "Comment",
                column: "TripId");

            migrationBuilder.CreateIndex(
                name: "IX_Comment_UserId1",
                table: "Comment",
                column: "UserId1");

            migrationBuilder.CreateIndex(
                name: "IX_Trip_TripCategoryId",
                table: "Trip",
                column: "TripCategoryId");

            migrationBuilder.CreateIndex(
                name: "IX_Trip_TripGuideId1",
                table: "Trip",
                column: "TripGuideId1");

            migrationBuilder.CreateIndex(
                name: "IX_TripReservation_TripId",
                table: "TripReservation",
                column: "TripId");

            migrationBuilder.CreateIndex(
                name: "IX_TripReservation_UserId1",
                table: "TripReservation",
                column: "UserId1");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Comment");

            migrationBuilder.DropTable(
                name: "TripReservation");

            migrationBuilder.DropTable(
                name: "Trip");

            migrationBuilder.DropTable(
                name: "TripCategory");
        }
    }
}
